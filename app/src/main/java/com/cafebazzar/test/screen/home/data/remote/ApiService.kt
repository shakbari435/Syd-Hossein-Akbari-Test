package com.cafebazzar.test.screen.home.data.remote

import com.cafebazzar.test.screen.home.data.model.GetMoviesResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("3/movie/upcoming?language=en-US")
    suspend fun getVideos(
        @Query("page") page: Int
    ): Response<GetMoviesResponseModel>
}