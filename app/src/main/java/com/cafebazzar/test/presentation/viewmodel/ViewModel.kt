package com.cafebazzar.test.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.cafebazzar.test.common.base.mvi.BaseViewModel
import com.cafebazzar.test.common.base.mvi.api.ApiResponse
import com.cafebazzar.test.data.model.GetMoviesResponseModel
import com.cafebazzar.test.domain.usecase.UseCase
import com.cafebazzar.test.presentation.intent.Intent
import com.cafebazzar.test.presentation.state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private var useCase: UseCase
) : BaseViewModel<Intent, ScreenState>() {
    private val _items = MutableStateFlow<MutableList<GetMoviesResponseModel.Movie>>(ArrayList())
    private val listState: ScreenState.ListState = ScreenState.ListState(_items, false, false)

    var page: Int = 1

    override fun createInitialState() = ScreenState.Idle

    override fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.GetMovies -> getMovies(intent.isRefresh)
        }
    }

    private fun getMovies(isRefresh: Boolean) {
        if (_items.value.isEmpty()) {
            setState { ScreenState.Loading }
        } else {
            setState {
                listState.copy(loading = true, isError = false)
            }
        }
        viewModelScope.launch {
            useCase.getMovies(page, isRefresh).catch {
                if (_items.value.isEmpty()) {
                    setState {
                        ScreenState.Error("${it.message}")
                    }
                } else {
                    setState {
                        listState.copy(loading = false, isError = true)
                    }
                }
            }.collect {
                when (it) {
                    is ApiResponse.Success -> {
                        if (!it.data.isNullOrEmpty()) {
                            _items.value.addAll(it.data)
                            page++
                        }
                        setState {
                            listState.copy(loading = false, isError = false)
                        }
                    }

                    is ApiResponse.Failure -> {
                        if (_items.value.isEmpty()) {
                            setState {
                                ScreenState.Error("${it.exception.message}")
                            }
                        } else {
                            setState {
                                listState.copy(loading = false, isError = true)
                            }
                        }
                    }
                }
            }
        }
    }
}