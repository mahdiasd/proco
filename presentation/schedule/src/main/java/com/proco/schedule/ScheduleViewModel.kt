package com.proco.schedule

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.proco.base.BaseViewModel
import com.proco.base.UiMessage
import com.proco.base.UiMessageType
import com.proco.domain.model.network.DataResult
import com.proco.domain.model.network.NetworkError
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.usecase.schedule.GetScheduleUseCase
import com.proco.domain.usecase.schedule.SaveScheduleUseCase
import com.proco.domain.usecase.user.GetLocalUserUseCase
import com.proco.domain.usecase.user.UpdatePriceUseCase
import com.proco.extention.findIndex
import com.proco.extention.safeAdd
import com.proco.extention.toLocalDate
import com.proco.ui.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val saveScheduleUseCase: SaveScheduleUseCase,
    private val getScheduleUseCase: GetScheduleUseCase,
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val updatePriceUseCase: UpdatePriceUseCase,
) : BaseViewModel<ScheduleUiState, ScheduleUiEvent>() {

    init {
        /* after user fetch from cache, getSchedule called for get mentor schedule*/
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            getLocalUserUseCase.executeSync(Unit).collect {
                when (it) {
                    is DataResult.Failure -> setState { currentState.copy(uiMessage = UiMessage.Network(NetworkError.AccessDenied)) }
                    is DataResult.Success -> {
                        setState { currentState.copy(user = it.data) }
                        getSchedule()
                    }
                }
            }
        }
    }

    private fun getSchedule() {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true, uiMessage = null, isSaved = false, showRetryGetSchedule = false) }
            getScheduleUseCase.executeSync(currentState.user!!.id).collect {
                when (it) {
                    is DataResult.Failure -> {
                        setState { currentState.copy(isLoading = false, uiMessage = UiMessage.Network(it.networkError), showRetryGetSchedule = true) }
                    }

                    is DataResult.Success -> {
                        setState {
                            currentState.copy(
                                isLoading = false,
                                showRetryGetSchedule = false,
                                data = it.data.toImmutableList(),
                                localSchedule = it.data.toMutableStateList()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun save() {
        viewModelScope.launch {
            setState { currentState.copy(saveScheduleLoading = true, uiMessage = null, isSaved = false) }
            saveScheduleUseCase.executeSync(currentState.localSchedule).collect {
                when (it) {
                    is DataResult.Failure -> {
                        setState { currentState.copy(uiMessage = UiMessage.Network(it.networkError), saveScheduleLoading = false) }
                    }

                    is DataResult.Success -> {
                        setState { currentState.copy(isSaved = true, saveScheduleLoading = false, showSaveButton = false) }
                    }
                }
            }
        }
    }

    private fun updatePrice(const: Int) {
        setState { currentState.copy(saveCostLoading = true, uiMessage = null) }
        viewModelScope.launch {
            updatePriceUseCase.executeSync(const).collect {
                when (it) {
                    is DataResult.Failure -> {
                        setState { currentState.copy(uiMessage = UiMessage.Network(it.networkError), saveCostLoading = false) }
                    }

                    is DataResult.Success -> {
                        setState {
                            currentState.copy(
                                saveCostLoading = false,
                                user = currentState.user?.copy(cost = const),
                                uiMessage = UiMessage.System(R.string.cost_updated, UiMessageType.Success)
                            )
                        }
                    }
                }
            }
        }
    }

    override fun createInitialState() = ScheduleUiState()

    override fun onTriggerEvent(event: ScheduleUiEvent) {
        when (event) {
            is ScheduleUiEvent.OnRetry -> {
                getSchedule()
            }

            is ScheduleUiEvent.OnSchedule -> {
                save()
            }

            is ScheduleUiEvent.OnUpdateCost -> {
                updatePrice(event.price)
            }

            is ScheduleUiEvent.OnAddHour -> {
                currentState.localSchedule.findIndex { it.date.compareTo(event.date.toLocalDate()) == 0 }?.let { index ->
                    val hours = currentState.localSchedule[index].hours.toMutableList().safeAdd(event.hourRange)
                    currentState.localSchedule[index] = currentState.localSchedule[index].copy(hours = hours.toImmutableList())
                    setState { currentState.copy(localSchedule = localSchedule, showSaveButton = true) }
                } ?: run {
                    setState {
                        currentState.copy(
                            localSchedule = localSchedule.apply { safeAdd(Schedule(event.date.toLocalDate(), hours = listOf(event.hourRange).toImmutableList())) },
                            showSaveButton = true
                        )
                    }
                }
            }

            is ScheduleUiEvent.OnRemoveHour -> {
                val updatedSchedules = currentState.localSchedule.toMutableStateList()

                val scheduleToUpdate = updatedSchedules.find { it.date.compareTo(event.date.toLocalDate()) == 0 } ?: return

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