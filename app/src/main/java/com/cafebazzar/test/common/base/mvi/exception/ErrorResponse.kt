package com.cafebazzar.test.common.base.mvi.exception

import com.cafebazzar.test.common.base.mvi.exception.ErrorResponseMessage
import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @SerializedName("http_status")
    val statusCode : Int = 0,

    @SerializedName("messages")
    val messages : List<ErrorResponseMessage> = listOf()
)
