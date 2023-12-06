package com.proco.search.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.proco.domain.model.filter.UserFilter
import com.proco.search.SearchScreen

const val searchRoute = "search_route"

fun NavGraphBuilder.searchScreen(
    onUserClick: () -> Unit,
    onFilter: () -> Unit,
    userFilter: UserFilter
) {
    composable(route = searchRoute) {
        SearchScreen(onItemClick = onUserClick, onFilter = onFilter)
    }
}

@Composable
fun NavGraphBuilder.SearchScreenCompose(
    onUserClick: () -> Unit,
    onFilter: () -> Unit,
) {
    SearchScreen(onItemClick = onUserClick, onFilter = onFilter)
}