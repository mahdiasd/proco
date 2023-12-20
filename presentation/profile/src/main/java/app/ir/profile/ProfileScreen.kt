package app.ir.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
import com.proco.domain.model.user.User
import com.proco.extention.animateClickable
import com.proco.extention.baseModifier
import com.proco.extention.coilCircle
import com.proco.theme.ProcoTheme
import com.proco.ui.R
import com.proco.ui.button.ProcoButton
import com.proco.ui.dash_line.HorizontalDashLine
import com.proco.ui.dialog_item.PriceDialog
import com.proco.ui.error.ErrorScreen
import com.proco.ui.loading.LoadingScreen
import com.proco.ui.spacer.CustomSpacer
import com.proco.ui.text.BodyLargeText
import com.proco.ui.text.BodyMediumText
import com.proco.ui.text.TitleLargeText
import com.proco.ui.text_field.ProcoTextField

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ProcoTheme {
        ProfileScreenContent(
            onBooking = { },
            onEdit = { },
            user = FakeData.user(),
            profileType = ProfileType.Self,
            onPrice = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DarkPreview() {
    ProcoTheme(darkTheme = true) {
        ProfileScreenContent(
            onBooking = { },
            onEdit = { },
            user = FakeData.user(),
            profileType = ProfileType.Public,
            onPrice = {}
        )
    }
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

        if (uiState.isLoading) {
            LoadingScreen()
        } else if (uiState.data == null) {
            ErrorScreen(modifier = Modifier.fillMaxSize(), errorMessage = uiState.uiMessage.toString())
        } else {
            ProfileScreenContent(
                user = uiState.data,
                onBooking = onBooking,
                onEdit = onEdit,
                profileType = uiState.profileType,
                onPrice = { vm.onTriggerEvent(ProfileViewEvent.OnChangePrice(it)) }
            )
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
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier.size(80.dp),
                model = coilCircle(data = user.avatar.ifEmpty { R.drawable.ic_placeholer_avatar }),
                contentDescription = "user avatar"
            )
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

            when (profileType) {
                ProfileType.Public -> {

                }

                ProfileType.Self -> {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(32.dp)
                                .padding(6.dp),
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.surface
                        )
                        Icon(
                            modifier = Modifier
                                .size(32.dp)
                                .animateClickable { isShowPriceDialog = true }
                                .padding(6.dp),
                            painter = painterResource(id = R.drawable.ic_dollar),
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            }
        }

        HorizontalDashLine(modifier = Modifier.fillMaxWidth())

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
                TitleLargeText(
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    text = stringResource(id = R.string.bio),
                    color = if (profileTab == ProfileTab.Bio) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
            })

            Tab(selected = false, onClick = { profileTab = ProfileTab.Overview }) {
                TitleLargeText(
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    text = stringResource(id = R.string.overview),
                    color = if (profileTab == ProfileTab.Overview) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
            }

            Tab(selected = false, onClick = { profileTab = ProfileTab.Schedule }) {
                TitleLargeText(
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    text = stringResource(id = R.string.schedule),
                    color = if (profileTab == ProfileTab.Schedule) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
            }
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            when (profileTab) {
                ProfileTab.Bio -> BioScreen(user)
                ProfileTab.Overview -> OverViewScreen(user)
                ProfileTab.Schedule -> ScheduleScreen(user = user)
            }

        }

        if (profileType == ProfileType.Public)
            ProcoButton(text = stringResource(id = R.string.booking), onClick = {})

        if (isShowPriceDialog) {
            if (savePriceLoading == false) {
                isShowPriceDialog = false
            } else {
                PriceDialog(onPrice = onPrice, onDismiss = { isShowPriceDialog = false }, isLoading = savePriceLoading ?: false)
            }
        }


    }


}


@Composable
private fun ScheduleScreen(user: User) {


}

@Composable
private fun OverViewScreen(user: User) {

    BodyLargeText(text = stringResource(R.string.job_title))
    ProcoTextField(
        modifier = Modifier.fillMaxWidth(),
        value = user.job.name,
        onValueChange = { },
        hint = stringResource(R.string.job_title),
        readOnly = true
    )
    CustomSpacer()
    BodyLargeText(text = stringResource(R.string.expertise))
    ProcoTextField(
        modifier = Modifier.fillMaxWidth(),
        value = user.expertise.name,
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

@Composable
fun BioScreen(user: User) {
    BodyMediumText(
        text = if (user.bio.isNullOrEmpty()) stringResource(id = R.string.empty_bio) else user.bio!!, textAlign = TextAlign.Justify,
        textStyle = MaterialTheme.typography.bodyMedium.copy(lineHeight = 30.sp)
    )

}



