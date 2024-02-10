package com.cafebazzar.test.presentation.intent

import com.cafebazzar.test.common.base.mvi.UiIntent

sealed class Intent : UiIntent {
    data class GetMovies(val isRefresh:Boolean = false) : Intent()
}