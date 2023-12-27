package app.ir.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.ir.main.MainScreen

const val mainRoute = "main_route"

fun NavGraphBuilder.mainScreen(
    searchScreen: @Composable () -> Unit,
    profileScreen: @Composable () -> Unit,
    scheduleScreen: @Composable () -> Unit,
    bookingScreen: @Composable () -> Unit,
    invoiceScreen: @Composable () -> Unit,
    navigateToLogin: () -> Unit

) {
    composable(route = mainRoute) {
        MainScreen(
            searchScreen = searchScreen,
            profileScreen = profileScreen,
            scheduleScreen = scheduleScreen,
            bookingScreen = bookingScreen,
            invoiceScreen = invoiceScreen,
            navigateToLogin = navigateToLogin
        )
    }
}