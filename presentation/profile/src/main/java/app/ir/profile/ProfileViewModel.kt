package app.ir.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.proco.base.BaseViewModel
import com.proco.base.UiMessage
import com.proco.base.UiMessageType
import com.proco.domain.fake_data.FakeData
import com.proco.domain.model.network.DataResult
import com.proco.domain.usecase.user.GetUserUseCase
import com.proco.domain.usecase.user.UpdatePriceUseCase
import com.proco.ui.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updatePriceUseCase: UpdatePriceUseCase,
    private val savedStateHandle: SavedStateHandle?
) : BaseViewModel<ProfileViewState, ProfileViewEvent>() {
    private var userId: Int? = null

    init {
        when (currentState.profileType) {
            is ProfileType.Public -> getUser()
            is ProfileType.Self -> getLocalUser()
        }
    }

    private fun getLocalUser() {
        viewModelScope.launch {
            getUserUseCase.executeSync(GetUserUseCase.DataSourceType.Local).collect {
                when (it) {
                    is DataResult.Failure -> setState { currentState.copy(uiMessage = UiMessage.Network(it.networkError)) }
                    is DataResult.Success -> {
                        userId = it.data.id
                        getUser()
                    }
                }
                // TODO: remove fake data
                setState { currentState.copy(isLoading = false, uiMessage = null, data = FakeData.users().first()) }

            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase.executeSync(GetUserUseCase.DataSourceType.Local).collect {
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
                                uiMessage = UiMessage.System(R.string.price_updated, UiMessageType.Success)
                            )
                        }
                    }
                }
            }
        }
    }


    override fun createInitialState(): ProfileViewState {
        userId = savedStateHandle?.get("id")
        return ProfileViewState(profileType = if (userId == null) ProfileType.Self else ProfileType.Public)
    }

    override fun onTriggerEvent(event: ProfileViewEvent) {
        when (event) {
            is ProfileViewEvent.OnChangePrice -> {
                updatePrice(event.price)
            }
        }
    }


}
