package com.proco.login

import androidx.compose.runtime.Stable
import com.proco.base.BaseUiState
import com.proco.base.UiEffect
import com.proco.base.UiEvent
import com.proco.domain.model.user.User

@Stable
data class LoginUiState(
    override val isLoading: Boolean = false,
    override val data: User? = null,
    override val alertMessage: String? = null,
    val username: String = "",
    val password: String = "",
) : BaseUiState<User>


sealed class LoginUiEvent : UiEvent {
    data object LoginClicked : LoginUiEvent()
}

sealed class LoginUiEffect : UiEffect {
    data class ShowToast(val message: String) : LoginUiEffect()
    data object EmptyEmail : LoginUiEffect()
    data object EmptyPassword : LoginUiEffect()
}
