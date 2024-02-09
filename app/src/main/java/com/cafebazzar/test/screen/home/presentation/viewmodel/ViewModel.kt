package com.cafebazzar.test.screen.home.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.cafebazzar.test.common.base.mvi.BaseViewModel
import com.cafebazzar.test.common.base.mvi.api.ApiResponse
import com.cafebazzar.test.screen.home.data.model.GetMoviesResponseModel
import com.cafebazzar.test.screen.home.domain.usecase.UseCase
import com.cafebazzar.test.screen.home.presentation.intent.Intent
import com.cafebazzar.test.screen.home.presentation.state.States
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private var useCase: UseCase
) : BaseViewModel<Intent, States>() {
    var isLoadMoreLoading = mutableStateOf(false)
    private val _items = MutableStateFlow<MutableList<GetMoviesResponseModel.Movie>>(ArrayList())


    var page: Int = 1

    override fun createInitialState() = States.Idle

    override fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.GetMovies -> getMovies(intent.isRefresh)
        }
    }
    private fun getMovies(isRefresh:Boolean) {
        if (_items.value.isEmpty()) {
            setState { States.Loading }
        } else {
            isLoadMoreLoading.value = true
        }
        viewModelScope.launch {
            useCase.getMovies(page,isRefresh).catch {
                isLoadMoreLoading.value = false
                if(_items.value.isEmpty()) {
                    setState {
                        States.Error("${it.message}")
                    }
                }
            }.collect {
                when (it) {
                    is ApiResponse.Success -> {
                        isLoadMoreLoading.value = false
                        if (!it.data.isNullOrEmpty()) {
                            _items.value.addAll(it.data)
                            setState {
                                States.Items(_items)
                            }
                            page++
                        } else {
                            setState {
                                States.Empty("")
                            }
                        }
                    }
                    is ApiResponse.Failure -> {
                        isLoadMoreLoading.value = false
                        if(_items.value.isEmpty()) {
                            setState {
                                States.Error("${it.exception.message}")
                            }
                        }
                    }
                }
            }
        }
    }


    fun updateScreenState(screenState: States) {
        setState {
            screenState
        }
    }
}