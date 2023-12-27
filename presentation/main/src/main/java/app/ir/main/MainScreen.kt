package app.ir.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proco.domain.model.user.UserType
import com.proco.theme.ProcoTheme
import com.proco.ui.R
import com.proco.ui.loading.LoadingScreen
import com.proco.ui.text.BodyMediumText


sealed class NavBarItem(val title: Int, val icon: Int) {
    // common between mentor and mentee
    data object Profile : NavBarItem(R.string.profile, R.drawable.ic_profile)
    data object Invoice : NavBarItem(R.string.invoice, R.drawable.ic_dollar)
    data object Booking : NavBarItem(R.string.booking, R.drawable.ic_booking)

    // for mentor
    data object Schedule : NavBarItem(R.string.schedule, R.drawable.ic_schedule)

    // for mentee
    data object Search : NavBarItem(R.string.search, R.drawable.ic_search)
}


@Preview(showBackground = true)
@Composable
private fun DarkPreview() {
    ProcoTheme(darkTheme = true) {
        BottomNavigation(
            userType = UserType.Mentee,
            searchScreen = { },
            profileScreen = { },
            scheduleScreen = { },
            bookingScreen = { },
            invoiceScreen = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LightPreview() {
    ProcoTheme(darkTheme = false) {
        BottomNavigation(
            userType = UserType.Mentee,
            searchScreen = { },
            profileScreen = { },
            scheduleScreen = { },
            bookingScreen = { },
            invoiceScreen = { },
        )
    }
}

@Composable
fun MainScreen(
    vm: MainViewModel = hiltViewModel(),
    searchScreen: @Composable () -> Unit,
    profileScreen: @Composable () -> Unit,
    scheduleScreen: @Composable () -> Unit,
    bookingScreen: @Composable () -> Unit,
    invoiceScreen: @Composable () -> Unit,
    navigateToLogin: () -> Unit
) {

    val uiState = vm.uiState.collectAsState().value

    when {
        uiState.userNotFound -> {
            navigateToLogin()
        }
        uiState.user != null -> {
            BottomNavigation(
                userType = uiState.user.type,
                searchScreen = searchScreen,
                profileScreen = profileScreen,
                scheduleScreen = scheduleScreen,
                bookingScreen = bookingScreen,
                invoiceScreen = invoiceScreen,
            )
        }
        else -> {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun BottomNavigation(
    userType: UserType,
    searchScreen: @Composable () -> Unit,
    profileScreen: @Composable () -> Unit,
    scheduleScreen: @Composable () -> Unit,
    bookingScreen: @Composable () -> Unit,
    invoiceScreen: @Composable () -> Unit,
) {
    var selectedItem: NavBarItem by remember {
        mutableStateOf(
            when (userType) {
                UserType.Mentee -> NavBarItem.Search
                UserType.Mentor -> NavBarItem.Profile
            }
        )
    }

    val itemClick: (NavBarItem) -> Unit = {
        selectedItem = it
    }

    val items = remember {
        mutableStateListOf(
            NavBarItem.Profile, NavBarItem.Invoice, NavBarItem.Booking,
            when (userType) {
                UserType.Mentee -> NavBarItem.Search
                UserType.Mentor -> NavBarItem.Schedule
            }
        )
    }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
                items.forEach {
                    AddItem(screen = it, onClick = { itemClick(it) }, it == selectedItem)
                }
            }
        },
    ) {
        Box(
            modifier = Modifier
                .padding(start = it.calculateLeftPadding(LayoutDirection.Ltr), end = it.calculateLeftPadding(LayoutDirection.Ltr), bottom = it.calculateBottomPadding())
                .fillMaxSize()
        ) {
            when (selectedItem) {
                NavBarItem.Search -> searchScreen()
                NavBarItem.Profile -> profileScreen()
                NavBarItem.Schedule -> scheduleScreen()
                NavBarItem.Booking -> bookingScreen()
                NavBarItem.Invoice -> invoiceScreen()
            }
        }
    }
}

@Composable
fun RowScope.AddItem(screen: NavBarItem, onClick: () -> Unit, isSelected: Boolean) {
    NavigationBarItem(
        label = {
            BodyMediumText(text = stringResource(id = screen.title), color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
        },
        icon = {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = screen.icon),
                contentDescription = stringResource(id = screen.title),
                colorFilter = ColorFilter.tint(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
            )
        },
        selected = isSelected,
        alwaysShowLabel = true,
        onClick = onClick,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.surface,
            unselectedTextColor = MaterialTheme.colorScheme.surface,
            indicatorColor = MaterialTheme.colorScheme.background
        )
    )
}