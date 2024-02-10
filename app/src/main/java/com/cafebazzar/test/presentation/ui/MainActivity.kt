package com.cafebazzar.test.presentation.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.cafebazzar.test.R
import com.cafebazzar.test.common.ui.theme.CafebazzarTheme
import com.cafebazzar.test.data.model.GetMoviesResponseModel
import com.cafebazzar.test.presentation.intent.Intent
import com.cafebazzar.test.presentation.state.ScreenState
import com.cafebazzar.test.presentation.ui.kit.HorizontalErrorView
import com.cafebazzar.test.presentation.ui.kit.ItemView
import com.cafebazzar.test.presentation.ui.kit.LoadingView
import com.cafebazzar.test.presentation.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalCoilApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ViewModel = hiltViewModel()
            fun LazyGridState.isScrolledToEnd() =
                layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

            val state: LazyGridState = rememberLazyGridState()
            val endOfListReached by remember {
                derivedStateOf {
                    state.isScrolledToEnd()
                }
            }
            val screenState = viewModel.uiState.collectAsState().value
            if (screenState is ScreenState.Idle) {
                viewModel.sendIntent(intent = Intent.GetMovies())
            }
            CafebazzarTheme(
                isLoading = screenState is ScreenState.Loading,
                isEmpty = screenState is ScreenState.Empty,
                isError = screenState is ScreenState.Error,
                onRetryClick = {
                    viewModel.sendIntent(
                        intent = Intent.GetMovies()
                    )
                },
                content = {
                    if (screenState is ScreenState.ListState) {
                        val list = remember(screenState.items) {
                            derivedStateOf {
                                screenState.items
                            }
                        }
                        LaunchedEffect(endOfListReached) {
                            if (!screenState.loading) {
                                viewModel.sendIntent(intent = Intent.GetMovies(isRefresh = true))
                            }
                        }
                        LoadMovies(
                            list.value,
                            state,
                            screenState.loading,
                            screenState.isError,
                            onRetryClick = {
                                viewModel.sendIntent(intent = Intent.GetMovies(isRefresh = true))
                            }
                        )
                    }
                }
            )
        }
    }

    @SuppressLint(
        "UnusedMaterialScaffoldPaddingParameter",
        "UnusedMaterial3ScaffoldPaddingParameter"
    )
    @ExperimentalCoilApi
    @Composable
    fun LoadMovies(
        list: MutableStateFlow<MutableList<GetMoviesResponseModel.Movie>>,
        state: LazyGridState,
        isLoadMoreLoading: Boolean,
        isLoadMoreError: Boolean,
        onRetryClick: () -> Unit
    ) {
        Scaffold(
            topBar = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 16.dp, end = 16.dp)
                ) {
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(text = "Discover", color = Color.Red)
                    Image(
                        modifier = Modifier.size(35.dp),
                        painter = painterResource(id = R.drawable.bazaar_logo_2_),
                        contentDescription = ""
                    )
                }
            },
        ) {
            val configuration = LocalConfiguration.current
            val gridSize = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                6
            } else {
                3
            }
            val fullSpan: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(gridSize) }
            LazyVerticalGrid(
                columns = GridCells.Fixed(gridSize),
                contentPadding = PaddingValues(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                state = state, modifier = Modifier.padding(top = 50.dp)
            ) {
                itemsIndexed(
                    items = list.value,
                    itemContent = { index, item ->
                        ItemView(
                            imageUrl = item.posterPath ?: "",
                            title = item.title ?: ""
                        )
                    }
                )
                if (isLoadMoreLoading) {
                    item(span = fullSpan) {
                        LoadingView()
                    }
                }
                if (isLoadMoreError) {
                    item(span = fullSpan) {
                        HorizontalErrorView {
                            onRetryClick.invoke()
                        }
                    }
                }
            }
        }
    }
}