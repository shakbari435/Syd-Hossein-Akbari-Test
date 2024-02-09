/*
package com.shakbari.test.screen.home.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.shakbari.test.common.ui.kit.AvatarImageWithCoil
import com.shakbari.test.common.ui.kit.LoadingView
import com.shakbari.test.common.ui.theme.CustomTheme
import com.shakbari.test.screen.destinations.DetailScreenDestination
import com.cafebazzar.test.screen.home.data.entity.GetMoviesResponseModel
import com.cafebazzar.test.screen.home.presentation.intent.Intent
import com.cafebazzar.test.screen.home.presentation.state.States
import com.cafebazzar.test.screen.home.presentation.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(ExperimentalCoilApi::class)
@Destination(start = true)
@Composable
internal fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: ViewModel = hiltViewModel()
) {

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
        viewModel.sendIntent(intent = Intent.GetVideos())
    }
    CustomTheme(
        isLoading = screenState is States.Loading,
        isEmpty = screenState is States.Empty,
        isError = screenState is States.Error,
        onRetryClick = {
            viewModel.sendIntent(
                intent = Intent.GetVideos()
            )
        },
        content = {
            if (screenState is States.Items) {
                val list = remember(screenState.items) {
                    derivedStateOf {
                        screenState.items
                    }
                }
                LoadHomeItem(
                    endOfListReached,
                    list.value,
                    state,
                    viewModel.isLoadMoreLoading,
                    navigator
                ) {
                    viewModel.sendIntent(intent = Intent.GetVideos(isRefresh = true))
                }
            }
        }
    )

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalCoilApi
@Composable
fun LoadHomeItem(
    endOfListReached: Boolean,
    list: MutableStateFlow<MutableList<GetMoviesResponseModel.Categoryvideos>>,
    state: LazyListState,
    isLoadMoreLoading: MutableState<Boolean>,
    navigator: DestinationsNavigator,
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
                                navigator.navigate(DetailScreenDestination(
                                    poster = item.bigPoster ?: "",
                                    itemText = item.title ?: "")
                                )
                            },
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AvatarImageWithCoil(
                                url = item.bigPoster ?: ""
                            )
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
                                Text(text = item.senderName ?: "Hossein Akbari", fontSize = 12.sp)
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
*/
