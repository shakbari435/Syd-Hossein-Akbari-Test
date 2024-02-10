package com.shakbari.test.screen.home.di


import android.content.Context
import androidx.room.Room
import com.cafebazzar.test.screen.home.data.local.MovieDao
import com.cafebazzar.test.screen.home.data.local.MovieDataBase
import com.cafebazzar.test.screen.home.data.remote.ApiService
import com.cafebazzar.test.screen.home.data.repository.RepositoryImpl
import com.cafebazzar.test.screen.home.data.source.LocalDataSource
import com.cafebazzar.test.screen.home.data.source.LocalDataSourceImpl
import com.cafebazzar.test.screen.home.data.source.RemoteDataSourceImpl
import com.cafebazzar.test.screen.home.data.source.RemoteDatasource
import com.cafebazzar.test.screen.home.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieDataBase =
        Room.databaseBuilder(
            context,
            MovieDataBase::class.java,
            "movie_table"
        ).fallbackToDestructiveMigration().build()


    @Provides
    fun provideOrigamiDao(videoDatabase: MovieDataBase): MovieDao {
        return videoDatabase.getMoviesDAO()
    }

    @Singleton
    @Provides
    fun provideOrigamiRemoteDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource = localDataSourceImpl


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(
        ApiService::class.java
    )

    @Singleton
    @Provides
    fun provideDataSource(apiHelper: RemoteDataSourceImpl): RemoteDatasource = apiHelper

    @Singleton
    @Provides
    fun provideRepository(
        repositoryImpl: RepositoryImpl
    ): Repository = repositoryImpl

}