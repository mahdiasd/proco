package com.proco.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import app.ir.main.navigation.mainRoute
import app.ir.main.navigation.mainScreen
import app.ir.profile.navigation.ProfileScreenCompose
import com.proco.filter.navigation.filterRoute
import com.proco.filter.navigation.filterScreen
import com.proco.login.navigation.loginScreen
import com.proco.register.navigation.registerRoute
import com.proco.register.navigation.registerScreen
import com.proco.schedule.navigation.ScheduleScreenCompose
import com.proco.schedule.navigation.scheduleRoute
import com.proco.schedule.navigation.scheduleScreen
import com.proco.search.navigation.SearchScreenCompose

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = mainRoute) {
        loginScreen(onRegister = {
            navController.navigate(registerRoute)
        })

        registerScreen(onRegister = {
            navController.navigate(scheduleRoute)
        })

        mainScreen(
            searchScreen = { SearchScreenCompose(onUserClick = { }, onFilter = { navController.navigate(filterRoute) }) },
            profileScreen = { ProfileScreenCompose(onBooking = {}, onEdit = {}) },
            scheduleScreen = { ScheduleScreenCompose() },
            bookingScreen = {},
            invoiceScreen = {},
        )

        filterScreen(onPopupBackStack = { navController.popBackStack() })


        scheduleScreen()
    }

}