package com.proco.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proco.base.BaseScreen
import com.proco.domain.model.schedule.HourRange
import com.proco.domain.model.schedule.Schedule
import com.proco.extention.animateClickable
import com.proco.extention.baseModifier
import com.proco.extention.dLog
import com.proco.extention.toHour
import com.proco.extention.toInstant
import com.proco.extention.toLocalDateTime
import com.proco.extention.toMinute
import com.proco.extention.withColor
import com.proco.schedule.time_picker_dialog.RangeTimePicker
import com.proco.theme.ProcoTheme
import com.proco.theme.white
import com.proco.ui.R
import com.proco.ui.button.ProcoButton
import com.proco.ui.dash_line.HorizontalDashLine
import com.proco.ui.text.BodyMediumText
import com.proco.ui.text.TitleMediumText
import com.proco.utils.ProcoGravity
import kotlinx.collections.immutable.toImmutableList
import java.time.Instant

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DarkPreview() {
    ProcoTheme(darkTheme = true) {
        ScheduleScreenContent(
            schedules = listOf<Schedule>().toMutableStateList(),
            onAddTime = { _, _ -> },
            onRemoveTime = { _, _ -> },
            onSave = {}
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    ProcoTheme(darkTheme = false) {
        ScheduleScreenContent(
            schedules = listOf<Schedule>().toMutableStateList(),
            onAddTime = { date, hours -> },
            onRemoveTime = { date, hours -> },
            onSave = {}
        )
    }
}


@Composable
fun ScheduleScreen(vm: ScheduleViewModel = hiltViewModel()) {
    val uiState = vm.uiState.collectAsState().value

    BaseScreen(
        modifier = Modifier.baseModifier(),
        alertMessage = uiState.alertMessage
    ) {
        ScheduleScreenContent(
            schedules = uiState.localSchedule,
            onAddTime = { date, hours ->
                vm.onTriggerEvent(ScheduleUiEvent.OnAddHour(date, hours))
            },
            onRemoveTime = { date, hours ->
                vm.onTriggerEvent(ScheduleUiEvent.OnRemoveHour(date, hours))
            },
            showSaveButton = uiState.showSaveButton,
            onSave = {vm.onTriggerEvent(ScheduleUiEvent.OnSchedule)}

            )
    }


}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun ScheduleScreenContent(
    schedules: SnapshotStateList<Schedule>,
    onAddTime: (Instant, HourRange) -> Unit,
    onRemoveTime: (Instant, HourRange) -> Unit,
    showSaveButton: Boolean = false,
    onSave: () -> Unit
) {

    val datePickerState = rememberDatePickerState(Instant.now().toEpochMilli())
    val selectedDate: Instant by remember(datePickerState.selectedDateMillis) { mutableStateOf(datePickerState.selectedDateMillis?.toInstant() ?: Instant.now()) }

    var isShowTimePicker by remember { mutableStateOf(false) }

    val hours =
        remember(datePickerState.selectedDateMillis, schedules) { derivedStateOf { schedules.find { it.date.toEpochMilli() == datePickerState.selectedDateMillis }?.hours } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
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
                .animateClickable { isShowTimePicker = true }
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TitleMediumText(modifier = Modifier, text = stringResource(id = R.string.schedule))
            BodyMediumText(
                modifier = Modifier
                    .wrapContentSize()
                    .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                text = stringResource(id = R.string.add_time),
                textStyle = MaterialTheme.typography.bodyMedium.withColor(white),
                textAlign = TextAlign.Center,
                icon = com.proco.schedule.R.drawable.ic_add,
                iconTint = white,
                iconGravity = ProcoGravity.Left,
                iconModifier = Modifier.size(12.dp),
                textBottomPadding = 2.dp,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            )
        }

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            hours.value?.forEach { hour ->
                HourRangeItem(hour, onRemove = { onRemoveTime(selectedDate, hour) })
            }
        }

        if (showSaveButton)
            ProcoButton(text = stringResource(id = R.string.save), onClick = onSave)
    }


    if (isShowTimePicker) {
        RangeTimePicker(
            hours = hours.value?.toImmutableList(),
            onSave = { start, end ->
                val a = HourRange(start.toInstant(selectedDate), end.toInstant(selectedDate))
                a.start.toEpochMilli().dLog("start")
                a.end.toEpochMilli().dLog("end")

                onAddTime(selectedDate, HourRange(start.toInstant(selectedDate), end.toInstant(selectedDate)))
                isShowTimePicker = false
            },
            onDismiss = { isShowTimePicker = false })
    }


}

@Composable
fun HourRangeItem(range: HourRange, onRemove: () -> Unit) {
    BodyMediumText(
        modifier = Modifier
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.secondary, shape = MaterialTheme.shapes.small)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.small)
            .padding(8.dp),
        text = "${range.start.toLocalDateTime().hour.toHour()}:${range.start.toLocalDateTime().minute.toMinute()} -- ${range.end.toLocalDateTime().hour.toHour()}:${range.end.toLocalDateTime().minute.toMinute()}",
        textStyle = MaterialTheme.typography.bodyMedium.withColor(),
        icon = R.drawable.ic_close,
        textBottomPadding = 3.dp,
        iconGravity = ProcoGravity.Left,
        iconModifier = Modifier
            .size(16.dp)
            .animateClickable { onRemove() }
    )
}


