package com.proco.schedule

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proco.base.BaseScreen
import com.proco.domain.model.schedule.HourRange
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.model.user.UserCache
import com.proco.extention.baseModifier
import com.proco.extention.dLog
import com.proco.extention.toInstant
import com.proco.extention.toLocalDate
import com.proco.schedule.time_picker_dialog.RangeTimePicker
import com.proco.theme.ProcoTheme
import com.proco.theme.white
import com.proco.ui.R
import com.proco.ui.button.ProcoSmallButton
import com.proco.ui.dash_line.HorizontalDashLine
import com.proco.ui.dialog_item.PriceDialog
import com.proco.ui.hour_range.HourRangeEditableItem
import com.proco.ui.loading.LoadingScreen
import com.proco.ui.text.LabelLargeText
import com.proco.ui.text.TitleMediumText
import com.proco.ui.vector.FailedScreen
import kotlinx.collections.immutable.toImmutableList
import java.time.Instant
import java.time.LocalDateTime

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DarkPreview() {
    Preview(true)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview(darkTheme: Boolean = false) {
    ProcoTheme(darkTheme = darkTheme) {
        ScheduleScreenContent(
            schedules = listOf<Schedule>().toMutableStateList(),
            onAddTime = { date, hours -> },
            onRemoveTime = { date, hours -> },
            onPrice = {},
            onSave = {},
            onRetry = {}
        )
    }
}


@Composable
fun ScheduleScreen(vm: ScheduleViewModel = hiltViewModel()) {
    val uiState = vm.uiState.collectAsState().value

    BaseScreen(
        modifier = Modifier.baseModifier(),
        uiMessage = uiState.uiMessage
    ) {
        ScheduleScreenContent(
            schedules = uiState.localSchedule,
            user = uiState.user,
            isLoading = uiState.isLoading,
            onAddTime = { date, hours ->
                vm.onTriggerEvent(ScheduleUiEvent.OnAddHour(date, hours))
            },
            onRemoveTime = { date, hours ->
                vm.onTriggerEvent(ScheduleUiEvent.OnRemoveHour(date, hours))
            },
            showSaveButton = uiState.showSaveButton,
            savePriceLoading = uiState.saveCostLoading,
            saveScheduleLoading = uiState.saveScheduleLoading,
            showRetryGetSchedule = uiState.showRetryGetSchedule,
            onPrice = { vm.onTriggerEvent(ScheduleUiEvent.OnUpdateCost(it)) },
            onSave = { vm.onTriggerEvent(ScheduleUiEvent.OnSchedule) },
            onRetry = { vm.onTriggerEvent(ScheduleUiEvent.OnRetry) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun ScheduleScreenContent(
    schedules: SnapshotStateList<Schedule>,
    isLoading: Boolean = false,
    user: UserCache? = null,
    onAddTime: (Instant, HourRange) -> Unit,
    onRemoveTime: (Instant, HourRange) -> Unit,
    showSaveButton: Boolean = false,
    onRetry: () -> Unit,
    onSave: () -> Unit,
    onPrice: (Int) -> Unit,
    showRetryGetSchedule: Boolean = false,
    savePriceLoading: Boolean? = null,
    saveScheduleLoading: Boolean? = null,
) {
    var isShowPriceDialog by remember(user) { mutableStateOf(user?.cost == 0) }

    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = Instant.now().toEpochMilli(), yearRange = IntRange(LocalDateTime.now().year, LocalDateTime.now().year))

    val selectedDate: Instant by remember(datePickerState.selectedDateMillis) { mutableStateOf(datePickerState.selectedDateMillis?.toInstant() ?: Instant.now()) }

    var isShowTimePicker by remember { mutableStateOf(false) }
    val showAddButton by remember(selectedDate) { derivedStateOf { selectedDate.isAfter(Instant.now()) || selectedDate.toLocalDate().compareTo(Instant.now().toLocalDate()) == 0 } }

    schedules.forEachIndexed { index, schedule ->
        schedule.date.compareTo(datePickerState.selectedDateMillis?.toInstant()?.toLocalDate()).dLog("index: $index -> ")
    }

    val hours =
        remember(datePickerState.selectedDateMillis, schedules) {
            derivedStateOf {
                schedules.filter {
                    it.date.compareTo(datePickerState.selectedDateMillis?.toInstant()?.toLocalDate()) == 0
                }.map { it.hours }.flatten().toImmutableList()
            }
        }


    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AnimatedVisibility(visible = showSaveButton) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(MaterialTheme.colorScheme.secondary, MaterialTheme.shapes.small)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LabelLargeText(text = stringResource(id = R.string.schedule_changed_need_to_save))
                ProcoSmallButton(text = stringResource(id = R.string.save), onClick = onSave, isLoading = saveScheduleLoading ?: false)
            }
        }

        DatePicker(
            state = datePickerState,
            title = null,
            headline = null,
            showModeToggle = false
        )

        HorizontalDashLine(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TitleMediumText(modifier = Modifier, text = stringResource(id = R.string.schedule))
            AnimatedVisibility(visible = showAddButton) {
                ProcoSmallButton(
                    text = stringResource(id = R.string.add_time),
                    onClick = { isShowTimePicker = true },
                    icon = R.drawable.ic_add,
                    iconTint = white,
                    iconModifier = Modifier.size(12.dp),
                    textBottomPadding = 2.dp,
                )
            }
        }

        if (isLoading) {
            LoadingScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        } else if (showRetryGetSchedule) {
            FailedScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                onRetry = onRetry
            )
        } else {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp)
            ) {
                hours.value.forEach { hour ->
                    HourRangeEditableItem(hour, onRemove = { onRemoveTime(selectedDate, hour) })
                }
            }
        }


    }


    if (isShowTimePicker) {
        RangeTimePicker(
            hours = hours.value.toImmutableList(),
            onSave = { start, end ->
                val a = HourRange(start.toInstant(selectedDate), end.toInstant(selectedDate))
                a.start.toEpochMilli().dLog("start")
                a.end.toEpochMilli().dLog("end")

                onAddTime(selectedDate, HourRange(start.toInstant(selectedDate), end.toInstant(selectedDate)))
                isShowTimePicker = false
            },
            onDismiss = { isShowTimePicker = false })
    }

    if (isShowPriceDialog) {
        PriceDialog(
            title = R.string.before_schedule_choose_price,
            onPrice = onPrice,
            onDismiss = { isShowPriceDialog = false },
            isLoading = savePriceLoading ?: false
        )
    }

}



