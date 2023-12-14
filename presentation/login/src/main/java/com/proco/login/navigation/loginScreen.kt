package com.proco.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.proco.login.LoginScreen

const val loginRoute = "login_route"

fun NavGraphBuilder.loginScreen(
    onRegister: () -> Unit,
    onLoggedIn: () -> Unit,
) {
    composable(route = loginRoute) {
        LoginScreen(onRegister = onRegister, onLoggedIn = onLoggedIn)
    }
}