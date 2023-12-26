package com.proco.login

import androidx.compose.runtime.Stable
import com.proco.base.UiEvent
import com.proco.base.UiMessage
import com.proco.base.UiState

@Stable
data class LoginUiState(
    val uiMessage: UiMessage? = null,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val email: String = "mahdi@yahoo.com",
    val password: String = "12345678",
) : UiState


sealed class LoginUiEvent : UiEvent {
    data object LoginClicked : LoginUiEvent()
}