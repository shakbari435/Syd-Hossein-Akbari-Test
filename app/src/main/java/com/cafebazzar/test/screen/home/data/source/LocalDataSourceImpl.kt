package com.cafebazzar.test.screen.home.data.source

import com.cafebazzar.test.common.base.mvi.api.ApiResponse
import com.cafebazzar.test.screen.home.data.model.GetMoviesResponseModel
import com.cafebazzar.test.screen.home.data.local.MovieDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val videoDao: MovieDao
) : LocalDataSource {

    override suspend fun getMovieLocalData(): Flow<ApiResponse<List<GetMoviesResponseModel.Movie>>> {
        return flow { videoDao.getVideos().collect { emit(ApiResponse.Success(it)) } }
    }

    override suspend fun insertMovie(videos: List<GetMoviesResponseModel.Movie>) {
        //videoDao.deleteVideo()
        videos.forEach {
            videoDao.insertVideo(it)
        }
    }

    override suspend fun sizeMovies(): Int =
        videoDao.sizeVideo()

}