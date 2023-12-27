package com.proco.schedule

import androidx.compose.runtime.Stable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.proco.base.BaseUiState
import com.proco.base.UiEvent
import com.proco.base.UiMessage
import com.proco.domain.model.schedule.HourRange
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.model.user.UserCache
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.time.Instant

@Stable
data class ScheduleUiState(
    override val isLoading: Boolean = false,
    override val data: ImmutableList<Schedule> = listOf<Schedule>().toImmutableList(),
    override val uiMessage: UiMessage? = null,
    val user: UserCache? = null,
    val localSchedule: SnapshotStateList<Schedule> = data.toMutableStateList(),
    val showSaveButton: Boolean = false,
    val saveCostLoading: Boolean? = null,
    val saveScheduleLoading: Boolean? = null,
    val isSaved: Boolean = false,
    val showRetryGetSchedule: Boolean = false
) : BaseUiState<ImmutableList<Schedule>>

sealed class ScheduleUiEvent : UiEvent {
    data object OnSchedule : ScheduleUiEvent()
    data object OnRetry : ScheduleUiEvent()
    data class OnAddHour(val date: Instant, val hourRange: HourRange) : ScheduleUiEvent()
    data class OnUpdateCost(val price: Int) : ScheduleUiEvent()
    data class OnRemoveHour(val date: Instant, val hourRange: HourRange) : ScheduleUiEvent()
}

