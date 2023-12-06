package com.proco.filter.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.proco.filter.FilterScreen

const val filterRoute = "filter_route"

fun NavGraphBuilder.filterScreen(
    onPopupBackStack: () -> Unit,
) {
    composable(route = filterRoute) {
        FilterScreen(onBack = onPopupBackStack)
    }
}