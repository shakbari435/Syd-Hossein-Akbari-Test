package com.cafebazzar.test.domain.usecase


import com.cafebazzar.test.common.base.mvi.api.ApiResponse
import com.cafebazzar.test.data.model.GetMoviesResponseModel
import com.cafebazzar.test.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UseCase @Inject constructor(private val repository: Repository) {

    suspend fun getMovies(page: Int, isRefresh: Boolean): Flow<ApiResponse<List<GetMoviesResponseModel.Movie>>> =
        repository.getMovies(page,isRefresh)

}