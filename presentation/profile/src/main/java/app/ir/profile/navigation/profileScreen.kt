package app.ir.profile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.ir.profile.ProfileScreen

const val profileRoute = "profile_route"

fun NavGraphBuilder.profileScreen(
    onBooking: () -> Unit,
    onEdit: () -> Unit,
) {
    composable(route = profileRoute, arguments = listOf(navArgument("id") { type = NavType.IntType })) {
        ProfileScreen(onBooking = onBooking, onEdit = onEdit)
    }
}

@Composable
fun NavGraphBuilder.ProfileScreenCompose(
    onBooking: () -> Unit,
    onEdit: () -> Unit,
) {
    ProfileScreen(onBooking = onBooking, onEdit = onEdit)
}