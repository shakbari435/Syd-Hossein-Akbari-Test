package com.cafebazzar.test.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cafebazzar.test.data.model.GetMoviesResponseModel

@Database(entities = [GetMoviesResponseModel.Movie::class], version = 4)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun getMoviesDAO(): MovieDao
}