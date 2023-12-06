package com.proco.register.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.proco.register.RegisterScreen

const val registerRoute = "register_route"

fun NavGraphBuilder.registerScreen(
    onRegister: () -> Unit,
) {
    composable(route = registerRoute) {
        RegisterScreen(onRegister = onRegister)
    }
}