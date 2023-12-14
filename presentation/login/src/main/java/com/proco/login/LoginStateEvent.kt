package com.proco.login

import androidx.compose.runtime.Stable
import com.proco.base.UiEvent
import com.proco.base.UiState

@Stable
data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val email: String = "mah@gmail.com",
    val password: String = "12345678",
) : UiState


sealed class LoginUiEvent : UiEvent {
    data object LoginClicked : LoginUiEvent()
}