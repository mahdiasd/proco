package app.ir.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.proco.base.BaseViewModel
import com.proco.base.UiMessage
import com.proco.base.UiMessageType
import com.proco.domain.model.network.DataResult
import com.proco.domain.usecase.schedule.GetScheduleUseCase
import com.proco.domain.usecase.user.GetLocalUserUseCase
import com.proco.domain.usecase.user.GetOnlineUserUseCase
import com.proco.domain.usecase.user.UpdatePriceUseCase
import com.proco.ui.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle?,

    private val getOnlineUserUseCase: GetOnlineUserUseCase,
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val updatePriceUseCase: UpdatePriceUseCase,
    private val getScheduleUseCase: GetScheduleUseCase

) : BaseViewModel<ProfileViewState, ProfileViewEvent>() {
    private var userId: Int? = null

    init {
        manageProfileType()
        when (currentState.profileType) {
            is ProfileType.Public -> {
                getOnlineUser()
                getSchedule()
            }

            is ProfileType.Self -> getLocalUser()
        }
    }

    private fun manageProfileType() {
        userId = savedStateHandle?.get("id")
        setState { currentState.copy(profileType = if (userId == null) ProfileType.Self else ProfileType.Public) }
    }

    private fun getSchedule() {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true, uiMessage = null, showRetryGetSchedule = false) }
            getScheduleUseCase.executeSync(userId!!).collect {
                when (it) {
                    is DataResult.Failure -> {
                        setState { currentState.copy(isLoading = false, uiMessage = UiMessage.Network(it.networkError), showRetryGetSchedule = true) }
                    }

                    is DataResult.Success -> {
                        setState {
                            currentState.copy(
                                isLoading = false,
                                showRetryGetSchedule = false,
                                schedule = it.data.toImmutableList(),
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getLocalUser() {
        viewModelScope.launch {
            getLocalUserUseCase.executeSync(Unit).collect {
                when (it) {
                    is DataResult.Failure -> setState { currentState.copy(uiMessage = UiMessage.Network(it.networkError)) }
                    is DataResult.Success -> {
                        userId = it.data.id
                        setState { currentState.copy(isLoading = false, uiMessage = null) }
                        getOnlineUser()
                        getSchedule()
                    }
                }
            }
        }
    }

    private fun getOnlineUser() {
        setState { currentState.copy(isLoading = true, uiMessage = null) }
        viewModelScope.launch {
            getOnlineUserUseCase.executeSync(userId!!).collect {
                when (it) {
                    is DataResult.Failure -> setState { currentState.copy(uiMessage = UiMessage.Network(it.networkError)) }
                    is DataResult.Success -> {
                        setState { currentState.copy(isLoading = false, uiMessage = null, data = it.data) }
                    }
                }
            }
        }
    }

    private fun updatePrice(price: Int) {
        setState { currentState.copy(savePriceLoading = true, uiMessage = null) }
        viewModelScope.launch {
            updatePriceUseCase.executeSync(price).collect {
                when (it) {
                    is DataResult.Failure -> {
                        setState { currentState.copy(uiMessage = UiMessage.Network(it.networkError), savePriceLoading = false) }
                    }

                    is DataResult.Success -> {
                        setState {
                            currentState.copy(
                                savePriceLoading = false,
                                uiMessage = UiMessage.System(R.string.cost_updated, UiMessageType.Success)
                            )
                        }
                    }
                }
            }
        }
    }


    override fun createInitialState() = ProfileViewState()

    override fun onTriggerEvent(event: ProfileViewEvent) {
        when (event) {
            is ProfileViewEvent.OnChangePrice -> {
                updatePrice(event.price)
            }
        }
    }


}
