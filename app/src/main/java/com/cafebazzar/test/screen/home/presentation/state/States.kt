package com.cafebazzar.test.screen.home.presentation.state

import com.cafebazzar.test.common.base.mvi.UiState
import com.cafebazzar.test.screen.home.data.model.GetMoviesResponseModel
import kotlinx.coroutines.flow.MutableStateFlow

sealed class States : UiState {
    data object Idle : States()
    data object Loading : States()
    data class ListState(
        val items: MutableStateFlow<MutableList<GetMoviesResponseModel.Movie>>,
        val loading:Boolean,
        val isError:Boolean
    ) : States()
    data class Error(val errorMessage: String?) : States()
    data class Empty(val emptyMessage: String?) : States()
}