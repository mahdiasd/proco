package app.ir.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.proco.base.BaseScreen
import com.proco.domain.fake_data.FakeData
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.model.user.User
import com.proco.domain.model.user.UserType
import com.proco.extention.animateClickable
import com.proco.extention.baseModifier
import com.proco.extention.coilCircle
import com.proco.theme.ProcoTheme
import com.proco.theme.white
import com.proco.ui.R
import com.proco.ui.button.ProcoButton
import com.proco.ui.dialog_item.PriceDialog
import com.proco.ui.error.ErrorScreen
import com.proco.ui.hour_range.HourRangeItem
import com.proco.ui.loading.LoadingScreen
import com.proco.ui.spacer.CustomSpacer
import com.proco.ui.text.BodyLargeText
import com.proco.ui.text.BodyMediumText
import com.proco.ui.text.TitleMediumText
import com.proco.ui.text_field.ProcoTextField
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate

@Preview(showBackground = true)
@Composable
private fun Preview(darkTheme: Boolean = false) {
    ProcoTheme(darkTheme = darkTheme) {
        BaseScreen(modifier = Modifier.baseModifier()) {
            ProfileScreenContent(
                onBooking = { },
                onEdit = { },
                user = FakeData.user(),
                profileType = ProfileType.Self,
                schedules = null,
                onPrice = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DarkPreview() {
    Preview(true)
}

@Composable
fun ProfileScreen(
    vm: ProfileViewModel = hiltViewModel(),
    onBooking: () -> Unit,
    onEdit: () -> Unit,
) {
    val uiState = vm.uiState.collectAsState().value

    BaseScreen(
        modifier = Modifier.baseModifier(),
        uiMessage = uiState.uiMessage
    ) {

        when {
            uiState.isLoading -> {
                LoadingScreen(modifier = Modifier.fillMaxSize())
            }
            uiState.data == null -> {
                ErrorScreen(modifier = Modifier.fillMaxSize(), errorMessage = uiState.uiMessage.toString())
            }
            else -> {
                ProfileScreenContent(
                    user = uiState.data,
                    onBooking = onBooking,
                    onEdit = onEdit,
                    profileType = uiState.profileType,
                    savePriceLoading = uiState.savePriceLoading,
                    schedules = uiState.schedule,
                    onPrice = { vm.onTriggerEvent(ProfileViewEvent.OnChangePrice(it)) }
                )
            }
        }

    }
}

private sealed class ProfileTab(val index: Int) {
    data object Bio : ProfileTab(0)
    data object Overview : ProfileTab(1)
    data object Schedule : ProfileTab(2)
}

@Composable
private fun ProfileScreenContent(
    user: User,
    schedules: ImmutableList<Schedule>?,
    profileType: ProfileType,
    onBooking: () -> Unit,
    onEdit: () -> Unit,
    onPrice: (Int) -> Unit,
    savePriceLoading: Boolean? = null,
) {
    var profileTab: ProfileTab by remember { mutableStateOf(ProfileTab.Bio) }
    var isShowPriceDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box {
                AsyncImage(
                    modifier = Modifier.size(80.dp),
                    model = coilCircle(data = user.avatar.ifEmpty { R.drawable.ic_placeholer_avatar }),
                    contentDescription = "user avatar"
                )

                if (profileType == ProfileType.Self) {
                    Icon(
                        modifier = Modifier
                            .align(alignment = Alignment.BottomEnd)
                            .background(MaterialTheme.colorScheme.secondary, CircleShape)
                            .size(32.dp)
                            .padding(8.dp),
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BodyMediumText(text = user.getFullName())
                BodyMediumText(text = "Android Developer")
            }

            if (user.type is UserType.Mentor) {
                if (user.cost == 0 && profileType is ProfileType.Self) {
                    Icon(
                        modifier = Modifier
                            .size(48.dp)
                            .animateClickable { isShowPriceDialog = true }
                            .padding(6.dp),
                        painter = painterResource(id = R.drawable.ic_dollar),
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.primary
                    )
                } else {
                    BodyLargeText(
                        modifier = Modifier
                            .size(40.dp, 40.dp)
                            .animateClickable { isShowPriceDialog = true }
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                            .padding(8.dp),
                        text = "${user.cost} $",
                        color = white,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        TabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = profileTab.index,
            containerColor = MaterialTheme.colorScheme.background,
            divider = {},
            indicator = {
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(it[profileTab.index])
                        .requiredWidth(48.dp)
                        .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp, bottomEnd = 2.dp, bottomStart = 2.dp))
                )
            }
        ) {
            Tab(selected = true, onClick = { profileTab = ProfileTab.Bio }, text = {
                TitleMediumText(
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    text = stringResource(id = R.string.bio),
                    color = if (profileTab == ProfileTab.Bio) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
            })

            Tab(selected = false, onClick = { profileTab = ProfileTab.Overview }) {
                TitleMediumText(
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    text = stringResource(id = R.string.overview),
                    color = if (profileTab == ProfileTab.Overview) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
            }

            if (user.type is UserType.Mentor) {
                Tab(selected = false, onClick = { profileTab = ProfileTab.Schedule }) {
                    TitleMediumText(
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 8.dp),
                        text = stringResource(id = R.string.schedule),
                        color = if (profileTab == ProfileTab.Schedule) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                    )
                }
            }
        }

        when (profileTab) {
            ProfileTab.Bio -> BioScreen(user)
            ProfileTab.Overview -> OverViewScreen(user)
            ProfileTab.Schedule -> schedules?.let { ScheduleScreen(schedules = it) }
        }

        if (profileType == ProfileType.Public)
            ProcoButton(text = stringResource(id = R.string.booking), onClick = {})

        if (isShowPriceDialog && profileType == ProfileType.Self) {
            if (savePriceLoading == false) {
                isShowPriceDialog = false
            } else {
                PriceDialog(onPrice = onPrice, onDismiss = { isShowPriceDialog = false }, isLoading = savePriceLoading ?: false)
            }
        }


    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ScheduleScreen(schedules: ImmutableList<Schedule>) {
    val dates = remember(schedules) { schedules.map { it.date }.toImmutableList() }

    var currentDate by remember { mutableStateOf(dates.firstOrNull()) }

    val hours = remember(currentDate) { schedules.find { it.date.compareTo(currentDate) == 0 }?.hours?.toImmutableList() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .clipScrollableContainer(Orientation.Horizontal),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(items = dates)
            {
                DateItem(localDate = it, isSelected = currentDate?.compareTo(it) == 0, onClick = { currentDate = it })
            }
        }

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            maxItemsInEachRow = 3,
            horizontalArrangement = Arrangement.SpaceAround,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            hours?.forEach { hour ->
                HourRangeItem(modifier = Modifier.weight(1f, false), range = hour, onClick = {})
            }
        }
    }

}

@Composable
fun DateItem(localDate: LocalDate, isSelected: Boolean, onClick: () -> Unit) {
    val showYear by remember(localDate) { mutableStateOf(LocalDate.now().year != localDate.year) }

    BodyMediumText(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .animateClickable(onClick)
            .then(
                if (isSelected) {
                    Modifier.background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                } else Modifier
            )
            .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
            .padding(8.dp),
        text = (if (showYear) "${localDate.year}\n" else "") + "${localDate.month.name.take(3)}\n${localDate.dayOfMonth}",
        textAlign = TextAlign.Center,
        color = if (isSelected) white else MaterialTheme.colorScheme.surface
    )
}

@Composable
private fun OverViewScreen(user: User) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        BodyLargeText(text = stringResource(R.string.job_title))
        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            value = user.job,
            onValueChange = { },
            hint = stringResource(R.string.job_title),
            readOnly = true
        )
        CustomSpacer()
        BodyLargeText(text = stringResource(R.string.expertise))
        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            value = user.expertises?.map { it.name }?.joinToString(" - ") ?: "",
            onValueChange = { },
            hint = stringResource(R.string.expertise),
            readOnly = true
        )

        CustomSpacer()
        BodyLargeText(text = stringResource(R.string.experience))
        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            value = user.experience.toString(),
            onValueChange = { },
            hint = stringResource(R.string.experience),
            readOnly = true
        )

        CustomSpacer()
        BodyLargeText(text = stringResource(R.string.company))
        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            value = user.company,
            onValueChange = { },
            hint = stringResource(R.string.company),
            readOnly = true
        )

        user.skills?.let { skills ->
            CustomSpacer()
            BodyLargeText(text = stringResource(R.string.skills))
            ProcoTextField(
                modifier = Modifier.fillMaxWidth(),
                value = skills.joinToString { "${it.name}  " },
                onValueChange = { },
                hint = stringResource(R.string.skills),
                readOnly = true
            )
        }
    }
}

@Composable
fun BioScreen(user: User) {
    BodyMediumText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        text = if (user.bio.isNullOrEmpty()) stringResource(id = R.string.empty_bio) else user.bio!!, textAlign = TextAlign.Justify,
        textStyle = MaterialTheme.typography.bodyMedium.copy(lineHeight = 30.sp)
    )

}



