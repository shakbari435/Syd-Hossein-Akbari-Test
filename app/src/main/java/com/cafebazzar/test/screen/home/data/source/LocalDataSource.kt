package com.cafebazzar.test.screen.home.data.source

import com.cafebazzar.test.common.base.mvi.api.ApiResponse
import com.cafebazzar.test.screen.home.data.model.GetMoviesResponseModel
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun getMovieLocalData() : Flow<ApiResponse<List<GetMoviesResponseModel.Movie>>>

    suspend fun insertMovie(videos: List<GetMoviesResponseModel.Movie>)

    suspend fun sizeMovies(): Int
}