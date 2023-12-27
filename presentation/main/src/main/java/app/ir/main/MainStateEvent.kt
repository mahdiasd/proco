package app.ir.main

import androidx.compose.runtime.Stable
import com.proco.base.UiEvent
import com.proco.base.UiState
import com.proco.domain.model.user.UserCache

@Stable
data class MainUiState(
    val user: UserCache? = null,
    val userNotFound: Boolean = false
) : UiState


sealed class MainUiEvent : UiEvent {

}
