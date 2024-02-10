package com.cafebazzar.test.domain.repository

import com.cafebazzar.test.common.base.mvi.api.ApiResponse
import com.cafebazzar.test.data.model.GetMoviesResponseModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getMovies(page: Int, isRefresh: Boolean): Flow<ApiResponse<List<GetMoviesResponseModel.Movie>>>
}