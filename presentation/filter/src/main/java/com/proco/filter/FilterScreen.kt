package com.proco.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proco.base.BaseScreen
import com.proco.domain.model.filter.UserFilter
import com.proco.domain.model.user.Country
import com.proco.domain.model.user.Gender
import com.proco.domain.model.user.Job
import com.proco.extention.anyNull
import com.proco.extention.baseModifier
import com.proco.extention.uiName
import com.proco.theme.ProcoTheme
import com.proco.ui.R
import com.proco.ui.bottom_dialog.ListBottomDialog
import com.proco.ui.button.ProcoButton
import com.proco.ui.dialog_item.CountryItem
import com.proco.ui.dialog_item.GenderItems
import com.proco.ui.dialog_item.JobItems
import com.proco.ui.loading.LoadingScreen
import com.proco.ui.text_field.ProcoTextField
import com.proco.ui.toolbar.ProcoToolbar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList


@Preview(showBackground = true)
@Composable
private fun Preview() {
    ProcoTheme {
        BaseScreen(modifier = Modifier.baseModifier()) {
            FilterScreenContent(UserFilter())
        }
    }
}

@Composable
fun FilterScreen(
    vm: FilterViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
) {
    val uiState = vm.uiState.collectAsState().value
    BaseScreen(modifier = Modifier.baseModifier()) {
        if (anyNull(uiState.jobs, uiState.countries, uiState.gender)) {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        } else {
            FilterScreenContent(
                userFilter = uiState.userFilter,
                jobs = uiState.jobs!!,
                countries = uiState.countries!!,
                genders = uiState.gender!!,
                onJobTitle = { vm.onTriggerEvent(FilterUiEvent.OnJobTitle(it)) },
                onCountry = { vm.onTriggerEvent(FilterUiEvent.OnCountry(it)) },
                onGender = { vm.onTriggerEvent(FilterUiEvent.OnGender(it)) },
                onSave = {
                    vm.onTriggerEvent(FilterUiEvent.OnSave)
                    onBack()
                },
                onBack = onBack,
                onClear = {
                    vm.onTriggerEvent(FilterUiEvent.OnClear)
                }
            )
        }
    }

}

private sealed interface DialogType {
    data object Country : DialogType
    data object JobTitle : DialogType
    data object Gender : DialogType
    data object None : DialogType
}

@Composable
private fun FilterScreenContent(
    userFilter: UserFilter,
    jobs: ImmutableList<Job> = listOf<Job>().toImmutableList(),
    countries: ImmutableList<Country> = listOf<Country>().toImmutableList(),
    genders: ImmutableList<Gender> = listOf<Gender>().toImmutableList(),
    onJobTitle: (Job) -> Unit = {},
    onCountry: (Country) -> Unit = {},
    onGender: (Gender) -> Unit = {},
    onSave: () -> Unit = {},
    onBack: () -> Unit = {},
    onClear: () -> Unit = {},
) {
    val context = LocalContext.current
    var dialogType by remember { mutableStateOf<DialogType>(DialogType.None) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        ProcoToolbar(
            title = R.string.filter,
            leftIcon = R.drawable.ic_back,
            rightIcon = R.drawable.ic_remove,
            onLeft = onBack,
            onRight = onClear
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            ProcoTextField(
                modifier = Modifier.fillMaxWidth(),
                onClick = { dialogType = DialogType.JobTitle },
                value = userFilter.job?.name ?: "",
                onValueChange = { },
                hint = stringResource(com.proco.ui.R.string.job_title),
                enabled = false,
                icon = R.drawable.ic_arrow
            )

            ProcoTextField(
                modifier = Modifier.fillMaxWidth(),
                onClick = { dialogType = DialogType.Country },
                value = userFilter.country?.name ?: "",
                onValueChange = { },
                hint = stringResource(com.proco.ui.R.string.country),
                enabled = false,
                icon = R.drawable.ic_arrow
            )

            ProcoTextField(
                modifier = Modifier.fillMaxWidth(),
                onClick = { dialogType = DialogType.Gender },
                value = userFilter.gender?.uiName()?.let { stringResource(id = it) } ?: "",
                onValueChange = { },
                hint = stringResource(com.proco.ui.R.string.gender),
                enabled = false,
                icon = R.drawable.ic_arrow
            )
        }

        ProcoButton(
            text = stringResource(id = R.string.save),
            onClick = onSave
        )

    }

    if (dialogType != DialogType.None) {
        ListBottomDialog(
            modifier = Modifier.fillMaxWidth(),
            listSize = when (dialogType) {
                DialogType.Country -> countries.size
                DialogType.Gender -> genders.size
                DialogType.JobTitle -> jobs.size
                DialogType.None -> 0
            },
            isShowSearch = false,
            onDismissDialog = { dialogType = DialogType.None },
            listItem = { index ->
                when (dialogType) {
                    DialogType.Country -> {
                        CountryItem(
                            item = countries[index],
                            onClick = {
                                dialogType = DialogType.None
                                onCountry(countries[index])
                            })
                    }

                    DialogType.Gender -> {
                        GenderItems(gender = genders[index], onClick = {
                            dialogType = DialogType.None
                            onGender(genders[index])
                        })
                    }

                    DialogType.JobTitle -> {
                        JobItems(
                            item = jobs[index],
                            onClick = {
                                dialogType = DialogType.None
                                onJobTitle(jobs[index])
                            })
                    }

                    DialogType.None -> {}
                }
            }
        )
    }

}

