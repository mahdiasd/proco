package app.ir.splash

import androidx.lifecycle.viewModelScope
import com.proco.base.BaseViewModel
import com.proco.domain.model.network.DataResult
import com.proco.domain.usecase.user.GetLocalUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val getLocalUserUseCase: GetLocalUserUseCase) : BaseViewModel<SplashUiState, SplashUiEvent>() {

    init {
        isLogin()
        getConfig()
    }

    private fun isLogin() {
        viewModelScope.launch {
            setState { currentState.copy(uiMessage = null) }
            getLocalUserUseCase.executeSync(Unit).collect {
                when (it) {
                    is DataResult.Success -> {
                        setState { currentState.copy(isLoggedIn = true) }
                    }

                    is DataResult.Failure -> {
                        setState { currentState.copy(isLoggedIn = false) }
                    }
                }
            }
        }
    }

    private fun getConfig() {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true) }
            delay(1000)
            setState { currentState.copy(updateState = UpdateState.NoUpdate, isLoading = false) }
        }
    }

    override fun createInitialState() = SplashUiState()

    override fun onTriggerEvent(event: SplashUiEvent) {

    }

}
