package app.ir.splash

import androidx.compose.runtime.Stable
import com.proco.base.UiEvent
import com.proco.base.UiMessage
import com.proco.base.UiState

@Stable
data class SplashUiState(
    val uiMessage: UiMessage? = null,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val updateState: UpdateState = UpdateState.Idle
) : UiState


sealed class SplashUiEvent : UiEvent {
    data object Retry : SplashUiEvent()
}

sealed class UpdateState {
    data object Idle : UpdateState()
    data object NoUpdate : UpdateState()
    data object Suggest : UpdateState()
    data object Force : UpdateState()

}