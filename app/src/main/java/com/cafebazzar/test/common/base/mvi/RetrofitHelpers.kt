package com.cafebazzar.test.common.base.mvi


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retry
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

fun <T> Flow<Response<T>>.handleExceptions(times: Long = 2): Flow<Response<T>> =
    retry(times) { throwable ->
//        Log.d("ER", "errorHandler : throwable = $throwable")
//        Log.d("ER", "errorHandler : throwable = ${throwable.message}")

        throwable is IOException
    }.catch { t: Throwable ->
        emit(createErrorResponse(t))
    }

fun <T> createErrorResponse(throwable: Throwable): Response<T> {
    val status = getServerErrorStatusCode(throwable)
    val responseBody = getServerErrorBody(throwable)
    return Response.error(status, responseBody)
}

fun getServerErrorStatusCode(throwable: Throwable): Int = when (throwable) {
    is TimeoutException -> STATUS_TIMEOUT
    is InterruptedIOException -> STATUS_INTERRUPTED
    is ConnectException -> STATUS_FAILED_CONNECT
//    is NoSuchElementException -> IccOpenLogicalChannelResponse.STATUS_NO_SUCH_ELEMENT
    is IllegalArgumentException -> STATUS_ILLEGAL_ARGUMENT
    is UnknownHostException -> STATUS_UNKNOWN_HOST
    else -> STATUS_UNKNOWN
}

fun getServerErrorBody(throwable: Throwable): ResponseBody {
    val type = "text/plain".toMediaType()//"text"

    val message: String = when (throwable) {
        is TimeoutException -> "TimeOut"
        is InterruptedIOException -> "Intrupted Connection"
        is ConnectException -> "Connection Failure"
        is NoSuchElementException -> "No Element Found"
        is IllegalArgumentException -> "Wrong Argument"
        else -> "Unknown Error"
    }

    return ResponseBody.create(type, message)
}