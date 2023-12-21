package app.ir.profile

import androidx.compose.runtime.Stable
import com.proco.base.BaseUiState
import com.proco.base.UiEvent
import com.proco.base.UiMessage
import com.proco.domain.fake_data.FakeData
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.model.user.User
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Stable
data class ProfileViewState(
    override val isLoading: Boolean = true,
    override val data: User? = null,
    override val uiMessage: UiMessage? = null,
    val profileType: ProfileType,
    val schedule: ImmutableList<Schedule>? = FakeData.schedules().toImmutableList(),
    val savePriceLoading: Boolean? = null,
) : BaseUiState<User>


sealed class ProfileViewEvent : UiEvent {
    data class OnChangePrice(val price: Int) : ProfileViewEvent()
}


