package com.proco.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proco.base.BaseScreen
import com.proco.domain.fake_data.FakeData
import com.proco.domain.model.schedule.DayName
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.model.time.HoursOfDay
import com.proco.extention.animateClickable
import com.proco.extention.baseModifier
import com.proco.extention.withColor
import com.proco.theme.ProcoTheme
import com.proco.theme.white
import com.proco.ui.text.BodyMediumText
import kotlinx.collections.immutable.toImmutableList

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    ProcoTheme {
        ScheduleScreenContent(
            currentDay = DayName.Saturday,
            schedules = FakeData.schedules().toMutableStateList(),
            onChangeDay = {},
            onSelectedHour = {},
            onSave = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DarkPreview() {
    ProcoTheme(darkTheme = true) {
        ScheduleScreenContent(
            currentDay = DayName.Saturday,
            schedules = FakeData.schedules().toMutableStateList(),
            onChangeDay = {},
            onSelectedHour = {},
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
            currentDay = uiState.currentDay,
            schedules = uiState.schedules,
            onChangeDay = { vm.onTriggerEvent(ScheduleUiEvent.OnDay(it)) },
            onSelectedHour = { vm.onTriggerEvent(ScheduleUiEvent.OnHour(it)) },
            onSave = {}
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScheduleScreenContent(
    currentDay: DayName,
    schedules: SnapshotStateList<Schedule>,
    onChangeDay: (DayName) -> Unit,
    onSelectedHour: (HoursOfDay) -> Unit,
    onSave: () -> Unit
) {
    val totalHoursOfDay = remember(Unit) { FakeData.hoursOfDay().toImmutableList() }
    val state = rememberDateRangePickerState()
    DateRangePicker(state = state)


}




@Composable
fun HoursOfDayItem(hoursOfDay: HoursOfDay, isSelected: Boolean, onClick: () -> Unit) {
    BodyMediumText(
        modifier = Modifier
            .padding(8.dp)
            .shadow(2.dp, MaterialTheme.shapes.small, spotColor = MaterialTheme.colorScheme.surface)
            .then(
                if (isSelected) {
                    Modifier.background(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.shapes.small
                    )
                } else {
                    Modifier.background(
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.shapes.small
                    )
                }
            )

            .padding(horizontal = 8.dp, vertical = 16.dp)
            .animateClickable(onClick),
        text = hoursOfDay.name,
        textAlign = TextAlign.Center,
        textStyle = MaterialTheme.typography.bodyMedium.withColor(
            if (isSelected) white else MaterialTheme.colorScheme.surface
        ).copy(fontSize = 12.sp),
    )
}

@Composable
fun DayOfWeekItem(daysOfWeek: Schedule, isSelected: Boolean, onClick: () -> Unit) {
    BodyMediumText(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .then(
                if (isSelected) {
                    Modifier.background(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.shapes.small
                    )
                } else {
                    Modifier.background(
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.shapes.small
                    )
                }
            )
            .padding(8.dp)
            .animateClickable(onClick),
        text = daysOfWeek.dayName.name,
        textAlign = TextAlign.Center,
        textStyle = MaterialTheme.typography.bodyMedium.withColor(
            if (isSelected) white else MaterialTheme.colorScheme.surface
        ).copy(fontSize = 12.sp),
    )
}

