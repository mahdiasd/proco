package com.proco.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import app.ir.main.navigation.mainRoute
import app.ir.main.navigation.mainScreen
import app.ir.profile.navigation.ProfileScreenCompose
import app.ir.profile.navigation.navigateToProfile
import app.ir.profile.navigation.profileRoute
import app.ir.profile.navigation.profileScreen
import com.proco.filter.navigation.filterRoute
import com.proco.filter.navigation.filterScreen
import com.proco.login.navigation.loginRoute
import com.proco.login.navigation.loginScreen
import com.proco.register.navigation.registerRoute
import com.proco.register.navigation.registerScreen
import com.proco.schedule.navigation.ScheduleScreenCompose
import com.proco.schedule.navigation.scheduleScreen
import com.proco.search.navigation.SearchScreenCompose
import com.proco.search.navigation.searchScreen
import com.proco.splash.navigation.splashRoute
import com.proco.splash.navigation.splashScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = splashRoute) {

        splashScreen(
            navigateToHome = {
                navController.navigate(mainRoute)
                {
                    popUpTo(splashRoute) {
                        this.inclusive = true
                    }
                }
            },
            navigateToLogin = {
                navController.navigate(loginRoute) {
                    popUpTo(splashRoute) {
                        this.inclusive = true
                    }
                }
            }
        )

        loginScreen(
            onRegister = {
                navController.navigate(registerRoute)
            },
            onLoggedIn = {
                navController.navigate(mainRoute)
            }
        )

        registerScreen(onRegister = {
            navController.navigate(mainRoute)
        })

        mainScreen(
            searchScreen = { SearchScreenCompose(onUserClick = { userId -> navController.navigateToProfile(userId) }, onFilter = { navController.navigate(filterRoute) }) },
            profileScreen = { ProfileScreenCompose(onBooking = {}, onEdit = {}) },
            scheduleScreen = { ScheduleScreenCompose() },
            bookingScreen = {},
            invoiceScreen = {},
            navigateToLogin = {
                navController.navigate(loginRoute)
                navController.popBackStack(loginRoute, false)
            }
        )

        filterScreen(onPopupBackStack = { navController.popBackStack() })

        searchScreen(
            onUserClick = {
                navController.navigate(profileRoute + "/${it}")
            },
            onFilter = { navController.navigate(filterRoute) }
        )

        profileScreen(onBooking = {}, onEdit = {})

        filterScreen { navController.popBackStack() }

        scheduleScreen()
    }
}
