package com.cafebazzar.test.common.base.mvi.api

sealed class ApiResponse<T> {

    class Success<T>(val data: T?) : ApiResponse<T>()

    class Failure<T>(val exception: Exception) : ApiResponse<T>()
}
