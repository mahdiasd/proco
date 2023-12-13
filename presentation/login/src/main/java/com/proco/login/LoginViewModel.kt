package com.proco.login

import androidx.lifecycle.viewModelScope
import com.proco.base.BaseViewModel
import com.proco.domain.model.network.DataResult
import com.proco.domain.model.network.getUiMessage
import com.proco.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val useCase: LoginUseCase) : BaseViewModel<LoginUiState, LoginUiEvent, LoginUiEffect>() {

    private fun login() {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true) }
            useCase.executeSync(LoginUseCase.LoginParam(currentState.username, currentState.password)).collect {
                when (it) {
                    is DataResult.Success -> {
                        setState { currentState.copy(isLoading = false, isLoggedIn = true) }
                    }

                    is DataResult.Failure -> {
                        setState { currentState.copy(isLoading = false) }
                        setEffect { LoginUiEffect.ShowToast(it.errorEntity.getUiMessage()) }
                    }
                }
            }
        }
    }

    private fun validate(): Boolean {
        when {
            currentState.username.isEmpty() -> setEffect { LoginUiEffect.EmptyEmail }
            currentState.password.isEmpty() -> setEffect { LoginUiEffect.EmptyPassword }
            else -> return true
        }

        return false
    }

    fun onUsernameChanged(username: String) {
        setState { currentState.copy(username = username) }
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
