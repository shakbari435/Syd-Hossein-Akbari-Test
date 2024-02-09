package com.cafebazzar.test.screen.home.data.source

import com.cafebazzar.test.common.base.mvi.api.ApiResponse
import com.cafebazzar.test.screen.home.data.model.GetMoviesResponseModel
import kotlinx.coroutines.flow.Flow

interface RemoteDatasource {
    suspend fun getMovies(page: Int): Flow<ApiResponse<GetMoviesResponseModel>>
}