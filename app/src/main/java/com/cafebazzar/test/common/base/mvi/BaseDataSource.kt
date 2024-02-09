package com.cafebazzar.test.common.base.mvi

import android.util.Log
import com.google.gson.Gson
import com.cafebazzar.test.common.base.mvi.exception.ErrorResponse
import com.cafebazzar.test.common.base.mvi.exception.ErrorResponseMessage
import com.cafebazzar.test.common.base.mvi.exception.OldErrorResponseMessage
import com.cafebazzar.test.common.base.mvi.exception.DomainErrorHolder
import okhttp3.ResponseBody
import retrofit2.Response

abstract class BaseDataSource {

    fun <T> analyzeError(response : Response<T>) : DomainErrorHolder {
        val statusCode = response.code()
        val errorList = getErrors(statusCode, response.errorBody())

        return if (response.isSuccessful && response.body() == null ) {
            DomainErrorHolder.EmptyResponse()
        }else {
            when (statusCode) {
                // force logout
                9090 -> {
                    DomainErrorHolder.ForceLogout()
                }
                400 -> {
                    DomainErrorHolder.BadRequest(errorList)
                }
                401 -> {
                    DomainErrorHolder.Authorization(errorList)
                }
                404 -> {
                    DomainErrorHolder.NotFound(errorList)
                }
                422 -> {
                    DomainErrorHolder.Validation(errorList)
                }
                429 -> {
                    DomainErrorHolder.RateLimit(errorList)
                }
                else -> {
                    DomainErrorHolder.ShowErrorMessages(errorList)
                }
            }
        }
    }

    open fun getErrors(statusCode : Int , errorBody: ResponseBody?): List<ErrorResponseMessage> {
        if (errorBody == null ) {
            return  emptyList()
        }

        val parser = Gson()
        val errorString = errorBody.string()
        var error : ErrorResponse = ErrorResponse()

        try {
             error = parser.fromJson(errorString, ErrorResponse::class.java)
        } catch (e: Exception) { // JsonSyntaxException
            e.printStackTrace()
            Log.d("BaseFetcher" , "ErrorResponse : errorString = $errorString")
        }

        try {
            val errorArray = parser.fromJson(errorString, Array<OldErrorResponseMessage>::class.java)
            error = ErrorResponse(
                statusCode = statusCode,
                messages = errorArray.toErrorResponseMessage()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("BaseFetcher" , "OldErrorResponseMessage : errorString = $errorString")

        }

        Log.d("BaseFetcher" , "error = $error")

        return if (error.messages.isNotEmpty()) {
            error.messages
        }else{
            emptyList()
        }
    }
}

fun Array<OldErrorResponseMessage>.toErrorResponseMessage() : List<ErrorResponseMessage> {
    return this.map {
        ErrorResponseMessage(
            error_code = it.error_code,
            fields = it.fields,
            message = it.message
        )
    }
}