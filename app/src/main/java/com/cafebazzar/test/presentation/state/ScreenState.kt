package com.cafebazzar.test.presentation.state

import com.cafebazzar.test.common.base.mvi.UiState
import com.cafebazzar.test.data.model.GetMoviesResponseModel
import kotlinx.coroutines.flow.MutableStateFlow

sealed class ScreenState : UiState {
    data object Idle : ScreenState()
    data object Loading : ScreenState()
    data class ListState(
        val items: MutableStateFlow<MutableList<GetMoviesResponseModel.Movie>>,
        val loading: Boolean,
        val isError: Boolean
    ) : ScreenState()

    data class Error(val errorMessage: String?) : ScreenState()
    data class Empty(val emptyMessage: String?) : ScreenState()
}