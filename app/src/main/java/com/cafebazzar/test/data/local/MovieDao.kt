package com.cafebazzar.test.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cafebazzar.test.data.model.GetMoviesResponseModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(moviesData: GetMoviesResponseModel.Movie)

    @Query("SELECT * FROM movie_table")
    fun getVideos(): Flow<List<GetMoviesResponseModel.Movie>>

    @Query("DELETE FROM movie_table")
    suspend fun deleteVideo()

    @Query("SELECT COUNT(*) FROM movie_table")
    suspend fun sizeVideo(): Int
}