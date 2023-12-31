package app.ir.profile

import androidx.compose.runtime.Stable
import com.proco.base.BaseUiState
import com.proco.base.UiEffect
import com.proco.base.UiEvent
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.model.user.User
import kotlinx.collections.immutable.ImmutableList

@Stable
data class ProfileViewState(
    override val isLoading: Boolean = true,
    override val data: User? = null,
    override val alertMessage: String? = null,
    val profileType: ProfileType,
    val schedule: ImmutableList<Schedule>? = null
) : BaseUiState<User>


sealed class ProfileViewEvent : UiEvent {
}

sealed class ProfileUiEffect : UiEffect {
    data class ShowError(val message: String) : ProfileUiEffect()
}

