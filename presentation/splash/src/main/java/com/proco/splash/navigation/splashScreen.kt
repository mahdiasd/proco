package com.proco.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.proco.splash.ProcoSplashScreen

const val splashRoute = "splash_route"
fun NavGraphBuilder.splashScreen(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    composable(route = splashRoute) {
        ProcoSplashScreen(navigateToHome, navigateToLogin)
    }
}