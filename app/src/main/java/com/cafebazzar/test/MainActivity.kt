package com.cafebazzar.test

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.cafebazzar.test.screen.home.data.model.GetMoviesResponseModel
import com.cafebazzar.test.screen.home.presentation.intent.Intent
import com.cafebazzar.test.screen.home.presentation.state.States
import com.cafebazzar.test.screen.home.presentation.viewmodel.ViewModel
import com.cafebazzar.test.ui.kit.LoadingView
import com.cafebazzar.test.ui.theme.CafebazzarTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalCoilApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ViewModel = hiltViewModel()
            fun LazyListState.isScrolledToEnd() =
                layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

            val state: LazyListState = rememberLazyListState()
            val endOfListReached by remember {
                derivedStateOf {
                    state.isScrolledToEnd()
                }
            }
            val screenState = viewModel.uiState.collectAsState().value
            if (screenState is States.Idle) {
                viewModel.sendIntent(intent = Intent.GetMovies())
            }
            CafebazzarTheme(
                isLoading = screenState is States.Loading,
                isEmpty = screenState is States.Empty,
                isError = screenState is States.Error,
                onRetryClick = {
                    viewModel.sendIntent(
                        intent = Intent.GetMovies()
                    )
                },
                content = {
                    if (screenState is States.Items) {
                        val list = remember(screenState.items) {
                            derivedStateOf {
                                screenState.items
                            }
                        }
                        LoadMovies(
                            endOfListReached,
                            list.value,
                            state,
                            viewModel.isLoadMoreLoading,
                        ) {
                            viewModel.sendIntent(intent = Intent.GetMovies(isRefresh = true))
                        }
                    }
                }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint(
        "UnusedMaterialScaffoldPaddingParameter",
        "UnusedMaterial3ScaffoldPaddingParameter"
    )
    @ExperimentalCoilApi
    @Composable
    fun LoadMovies(
        endOfListReached: Boolean,
        list: MutableStateFlow<MutableList<GetMoviesResponseModel.Movie>>,
        state: LazyListState,
        isLoadMoreLoading: MutableState<Boolean>,
        onLoadMore: () -> Unit
    ) {
        LaunchedEffect(endOfListReached) {
            onLoadMore()
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row {
                            Text(text = "Created by")
                            Text(text = " Syd Hossien Akbari")
                        }
                    }
                )
            },
        ) {
            LazyColumn(state = state, modifier = Modifier.padding(bottom = 50.dp)) {
                itemsIndexed(
                    items = list.value,
                    itemContent = { index, item ->
                        Card(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                                .clickable {

                                },
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = item.title ?: "Test App",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Spacer(modifier = Modifier.padding(6.dp))
                                    Text(
                                        text = item.originalTitle ?: "Hossein Akbari",
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                )
                if (isLoadMoreLoading.value) {
                    item {
                        LoadingView()
                    }
                }
            }
        }
    }

}