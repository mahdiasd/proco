package com.proco.schedule

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.proco.base.BaseViewModel
import com.proco.base.UiMessage
import com.proco.domain.model.network.DataResult
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.usecase.ScheduleUseCase
import com.proco.extention.findIndex
import com.proco.extention.safeAdd
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(private val useCase: ScheduleUseCase) : BaseViewModel<ScheduleUiState, ScheduleUiEvent>() {

    private fun save() {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true, uiMessage = null, isSaved = false) }
            useCase.executeSync(currentState.localSchedule).collect {
                when (it) {
                    is DataResult.Failure -> {
                        setState { currentState.copy(uiMessage = UiMessage.Network(it.networkError)) }
                    }

                    is DataResult.Success -> {
                        setState { currentState.copy(isSaved = true) }
                    }
                }
            }
        }
    }


    override fun createInitialState() = ScheduleUiState()

    override fun onTriggerEvent(event: ScheduleUiEvent) {
        when (event) {
            is ScheduleUiEvent.OnSchedule -> {
                save()
            }

            is ScheduleUiEvent.OnAddHour -> {
                currentState.localSchedule.findIndex { it.date.toEpochMilli() == event.date.toEpochMilli() }?.let { index ->
                    val hours = currentState.localSchedule[index].hours.toMutableList().safeAdd(event.hourRange)
                    currentState.localSchedule[index] = currentState.localSchedule[index].copy(hours = hours.toImmutableList())
                    setState { currentState.copy(localSchedule = localSchedule, showSaveButton = true) }
                } ?: run {
                    setState {
                        currentState.copy(
                            localSchedule = localSchedule.apply { safeAdd(Schedule(event.date, hours = listOf(event.hourRange).toImmutableList())) },
                            showSaveButton = true
                        )
                    }
                }
            }

            is ScheduleUiEvent.OnRemoveHour -> {
                val updatedSchedules = currentState.localSchedule.toMutableStateList()

                val scheduleToUpdate = updatedSchedules.find { it.date == event.date }

                if (scheduleToUpdate != null) {
                    if (scheduleToUpdate.hours.size == 1) {
                        setState { currentState.copy(localSchedule = localSchedule.apply { remove(scheduleToUpdate) }, showSaveButton = true) }
                    } else {
                        val updatedHours = scheduleToUpdate.hours.toMutableStateList()
                        updatedHours.remove(event.hourRange)

                        updatedSchedules[updatedSchedules.indexOf(scheduleToUpdate)] = scheduleToUpdate.copy(hours = updatedHours.toImmutableList())
                        setState { currentState.copy(localSchedule = updatedSchedules, showSaveButton = true) }
                    }
                }
            }
        }
    }

}