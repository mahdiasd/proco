package com.proco.login

import androidx.lifecycle.viewModelScope
import com.proco.base.BaseViewModel
import com.proco.base.UiMessage
import com.proco.domain.model.network.DataResult
import com.proco.domain.usecase.auth.LoginUseCase
import com.proco.ui.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val useCase: LoginUseCase) : BaseViewModel<LoginUiState, LoginUiEvent>() {

    private fun login() {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true , uiMessage = null) }
            useCase.executeSync(LoginUseCase.LoginParam(currentState.email, currentState.password)).collect {
                when (it) {
                    is DataResult.Success -> {
                        setState { currentState.copy(isLoading = false, isLoggedIn = true) }
                    }

                    is DataResult.Failure -> {
                        setState { currentState.copy(isLoading = false, uiMessage = UiMessage.Network(it.networkError)) }
                    }
                }
            }
        }
    }

    private fun validate(): Boolean {
        setState { currentState.copy(uiMessage = null) }
        when {
            currentState.email.isEmpty() -> setState { currentState.copy(uiMessage = UiMessage.System(R.string.empty_email)) }
            currentState.password.isEmpty() -> setState { currentState.copy(uiMessage = UiMessage.System(R.string.password)) }
            else -> return true
        }

        return false
    }

    fun onUsernameChanged(username: String) {
        setState { currentState.copy(email = username) }
    }

    fun onPasswordChanged(password: String) {
        setState { currentState.copy(password = password) }
    }


    override fun createInitialState() = LoginUiState()

    override fun onTriggerEvent(event: LoginUiEvent) {
        when (event) {
            LoginUiEvent.LoginClicked -> {
                if (validate()) login()
            }
        }
    }

}
