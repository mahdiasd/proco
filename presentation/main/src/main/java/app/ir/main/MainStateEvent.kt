package app.ir.main

import androidx.compose.runtime.Stable
import com.proco.base.UiEffect
import com.proco.base.UiEvent
import com.proco.base.UiState
import com.proco.domain.model.user.User

@Stable
data class MainUiState(
    val user: User? = null,
) : UiState


sealed class MainUiEvent : UiEvent {

}

sealed class MainUiEffect : UiEffect {
    data class ShowError(val message: String) : MainUiEffect()
}
