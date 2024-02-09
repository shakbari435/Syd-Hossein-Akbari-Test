package com.cafebazzar.test.screen.home.data.repository

import com.cafebazzar.test.common.base.mvi.api.ApiResponse
import com.cafebazzar.test.screen.home.data.model.GetMoviesResponseModel
import com.cafebazzar.test.screen.home.data.source.LocalDataSource
import com.cafebazzar.test.screen.home.data.source.RemoteDatasource
import com.cafebazzar.test.screen.home.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDatasource: RemoteDatasource
) : Repository {

    override suspend fun getMovies(
        page: Int,
        isRefresh: Boolean
    ): Flow<ApiResponse<List<GetMoviesResponseModel.Movie>>> {
        return if (localDataSource.sizeMovies() != 0 && !isRefresh) {
            localDataSource.getMovieLocalData()
        } else {
            flow {
                remoteDatasource.getMovies(page).collect {
                    when (it) {
                        is ApiResponse.Success -> {
                            it.data?.movies?.let { it1 -> localDataSource.insertMovie(it1) }
                            emit(ApiResponse.Success(it.data!!.movies))
                        }

                        is ApiResponse.Failure -> emit(ApiResponse.Failure(it.exception))
                    }
                }
            }
        }
    }

}

