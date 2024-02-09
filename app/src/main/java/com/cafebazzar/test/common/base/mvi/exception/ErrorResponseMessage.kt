package com.cafebazzar.test.common.base.mvi.exception

import com.google.gson.annotations.SerializedName


data class ErrorResponseMessage(
        @SerializedName("code") val error_code: Int = 0, // the code backend developer determine it
        @SerializedName("fields") val fields: List<String> = listOf(),
        @SerializedName("message") val message: String = "خطا در برقراری ارتباط"
)



data class OldErrorResponseMessage(
        @SerializedName("error_code") val error_code: Int = 0, // the code backend developer determine it
        @SerializedName("fields") val fields: List<String> = listOf(),
        @SerializedName("message") val message: String = "خطا در برقراری ارتباط"
)