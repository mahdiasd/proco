package com.proco.schedule

import androidx.lifecycle.viewModelScope
import com.proco.base.BaseViewModel
import com.proco.domain.model.time.HoursOfDay
import com.proco.domain.usecase.ScheduleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(private val useCase: ScheduleUseCase) : BaseViewModel<ScheduleUiState, ScheduleUiEvent, ScheduleUiEffect>() {

    private fun save() {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true, alertMessage = null) }
            useCase.executeSync(currentState.data).collect {
            }
        }
    }


    override fun createInitialState() = ScheduleUiState()

    override fun onTriggerEvent(event: ScheduleUiEvent) {
        when (event) {
            is ScheduleUiEvent.OnDay -> setState { currentState.copy(currentDay = event.dayName) }
            is ScheduleUiEvent.OnSchedule -> save()
            is ScheduleUiEvent.OnHour -> {
                updateSchedule(event.hoursOfDay)
            }
        }
    }

    private fun updateSchedule(hoursOfDay: HoursOfDay) {
        currentState.schedules.find { it.dayName == currentState.currentDay }?.let { schedule ->
            schedule.hours.find { it.id == hoursOfDay.id }?.let { hoursOfDay ->
                schedule.hours.remove(hoursOfDay)
            } ?: run {
                schedule.hours.add(hoursOfDay)
            }
        }
    }
}