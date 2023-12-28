package com.proco.search.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.proco.search.SearchScreen

const val searchRoute = "search_route"

fun NavGraphBuilder.searchScreen(
    onUserClick: (Int) -> Unit,
    onFilter: () -> Unit,
) {
    composable(route = searchRoute) {
        SearchScreen(onItemClick = onUserClick, onFilter = onFilter)
    }
}

@Composable
fun NavGraphBuilder.SearchScreenCompose(
    onUserClick: (Int) -> Unit,
    onFilter: () -> Unit,
) {
    SearchScreen(onItemClick = onUserClick, onFilter = onFilter)
}