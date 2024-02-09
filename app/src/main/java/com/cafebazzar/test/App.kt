package com.cafebazzar.test

import android.app.Application
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent

@HiltAndroidApp
@Module
@InstallIn(SingletonComponent::class)
class App : Application() {

}