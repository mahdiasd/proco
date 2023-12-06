package com.proco.schedule.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.proco.schedule.ScheduleScreen

const val scheduleRoute = "schedule_route"

fun NavGraphBuilder.scheduleScreen(
) {
    composable(route = scheduleRoute) {
        ScheduleScreen()
    }
}

@Composable
fun NavGraphBuilder.ScheduleScreenCompose(
) {
    ScheduleScreen()
}