package com.cafebazzar.test.data.source

import com.cafebazzar.test.common.base.mvi.BaseDataSource
import com.cafebazzar.test.common.base.mvi.api.ApiResponse
import com.cafebazzar.test.common.base.mvi.handleExceptions
import com.cafebazzar.test.data.model.GetMoviesResponseModel
import com.cafebazzar.test.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    RemoteDatasource, BaseDataSource() {

    override suspend fun getMovies(page: Int): Flow<ApiResponse<GetMoviesResponseModel>> {
        return flow {
            emit(
                apiService.getVideos(page)
            )
        }
            .handleExceptions()
            .map {
                if (it.isSuccessful && it.body() != null) {
                    ApiResponse.Success(it.body())
                } else {
                    ApiResponse.Failure(analyzeError(it))
                }
            }
    }
}

