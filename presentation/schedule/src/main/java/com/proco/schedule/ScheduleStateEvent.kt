package com.proco.schedule

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.proco.base.BaseUiState
import com.proco.base.UiEffect
import com.proco.base.UiEvent
import com.proco.domain.fake_data.FakeData
import com.proco.domain.model.schedule.DayName
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.model.schedule.ScheduleDto
import com.proco.domain.model.time.HoursOfDay

@Stable
data class ScheduleUiState(
    val currentDay: DayName = DayName.Saturday,
    val schedules: SnapshotStateList<Schedule> = FakeData.schedules().toMutableStateList(),
    override val isLoading: Boolean = false,
    override val data: SnapshotStateList<ScheduleDto> = mutableStateListOf(),
    override val alertMessage: String? = ""
) : BaseUiState<SnapshotStateList<ScheduleDto>>

sealed class ScheduleUiEvent : UiEvent {
    data object OnSchedule : ScheduleUiEvent()
    data class OnDay(val dayName: DayName) : ScheduleUiEvent()
    data class OnHour(val hoursOfDay: HoursOfDay) : ScheduleUiEvent()
}

sealed class ScheduleUiEffect : UiEffect {
    data class ShowToast(val message: String) : ScheduleUiEffect()
    data object EmptySchedule : ScheduleUiEffect()
}
