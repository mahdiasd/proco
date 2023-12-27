package com.proco.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.proco.extention.OnBottomReached
import com.proco.theme.white
import com.proco.ui.vector.EmptyScreen
import com.proco.ui.vector.FailedScreen

@Preview(showBackground = true)
@Composable
private fun ListPreview() {
    SwipeList(modifier = Modifier.fillMaxWidth(), isRefreshing = false, isLoadMore = false, listSize = 0, onRefresh = { }, onLoadMore = { }) {

    }
}

@Composable
fun SwipeList(
    modifier: Modifier,
    isRefreshing: Boolean,
    isLoadMore: Boolean,
    listSize: Int?,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    retry: (() -> Unit)? = null,
    key: ((Int) -> Any)? = null,
    contentPadding: PaddingValues = PaddingValues(bottom = 48.dp),
    screenToShow: @Composable (index: Int) -> Unit
) {
    val showEmptyVector = ((!isLoadMore && !isRefreshing) && listSize != null && listSize == 0)
    val showErrorVector = ((!isLoadMore && !isRefreshing) && listSize == null)

    val listState = rememberLazyListState()
    SwipeRefresh(
        modifier = modifier,
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = {
            onRefresh.invoke()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            LazyColumn(
                state = listState,
                contentPadding = contentPadding,
                modifier = Modifier
                    .clipScrollableContainer(Orientation.Vertical)
                    .align(Alignment.TopCenter)
            ) {
                items(listSize ?: 0, key = key) { index ->
                    screenToShow(index)
                }
            }

            if (isLoadMore) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .shadow(2.dp, CircleShape, spotColor = MaterialTheme.colorScheme.primary)
                        .align(Alignment.BottomCenter)
                        .size(size = 32.dp)
                        .padding(4.dp)
                        .background(white, shape = CircleShape)
                        .padding(bottom = 8.dp),
                    strokeWidth = 3.dp, color = MaterialTheme.colorScheme.primary
                )
            }

            if (showEmptyVector) {
                EmptyScreen(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center))
            }

            if (showErrorVector) {
                FailedScreen(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center), onRetry = onRefresh)
            }
        }
    }

    listState.OnBottomReached {
        onLoadMore.invoke()
    }
}

@Composable
fun SwipeVerticalGrid(
    modifier: Modifier,
    isRefreshing: Boolean,
    isLoadMore: Boolean,
    listSize: Int?,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    paddingValues: PaddingValues = PaddingValues(16.dp),
    key: ((Int) -> Any)? = null,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(12.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(16.dp),
    columns: GridCells = GridCells.Fixed(2),
    screenToShow: @Composable (index: Int) -> Unit
) {
    val showEmptyVector = ((!isLoadMore && !isRefreshing) && listSize != null && listSize == 0)
    val showErrorVector = ((!isLoadMore && !isRefreshing) && listSize == null)

    val listState = rememberLazyListState()
    SwipeRefresh(
        modifier = modifier.fillMaxSize(),
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = {
            onRefresh.invoke()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 2.dp)
                    .clipScrollableContainer(Orientation.Vertical),
                contentPadding = paddingValues,
                verticalArrangement = verticalArrangement,
                horizontalArrangement = horizontalArrangement,
                columns = columns,
                content = {
                    items(listSize ?: 0, key = key) { index ->
                        screenToShow(index)
                    }
                })

            if (isLoadMore) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .shadow(2.dp, CircleShape, spotColor = MaterialTheme.colorScheme.primary)
                        .align(Alignment.BottomCenter)
                        .size(size = 32.dp)
                        .padding(4.dp)
                        .background(white, shape = CircleShape)
                        .padding(bottom = 8.dp),
                    strokeWidth = 3.dp, color = MaterialTheme.colorScheme.primary
                )
            }

            if (showEmptyVector) {
//                CatContent()
            }

            if (showErrorVector) {
//                DogContent(onClick = { onRefresh() })
            }

        }
    }

    listState.OnBottomReached {
        onLoadMore.invoke()
    }
}
