package com.proco.login

import androidx.compose.runtime.Stable
import com.proco.base.UiEffect
import com.proco.base.UiEvent
import com.proco.base.UiState

@Stable
data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val username: String = "mah@gmail.com",
    val password: String = "12345678",
) : UiState


sealed class LoginUiEvent : UiEvent {
    data object LoginClicked : LoginUiEvent()
}

sealed class LoginUiEffect : UiEffect {
    data class ShowToast(val message: String) : LoginUiEffect()
    data object EmptyEmail : LoginUiEffect()
    data object EmptyPassword : LoginUiEffect()
}
