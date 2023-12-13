package com.proco.search

import androidx.compose.runtime.Stable
import com.proco.base.BaseUiState
import com.proco.base.UiEffect
import com.proco.base.UiEvent
import com.proco.domain.model.filter.UserFilter
import com.proco.domain.model.user.User
import com.proco.utils.MyConstant
import kotlinx.collections.immutable.ImmutableList

@Stable
data class SearchViewState(
    override val isLoading: Boolean = false,
    override val data: ImmutableList<User>? = null,
    override val alertMessage: String? = null,
    val searchText: String = "",
    val currentStep: Int = 0,
    val userFilter: UserFilter? = UserFilter(),
    val page: Int = MyConstant.defaultPageNumber,
) : BaseUiState<ImmutableList<User>?>


sealed class SearchViewEvent : UiEvent {
    data class OnTyping(val text: String) : SearchViewEvent()
}

sealed class SearchUiEffect : UiEffect {
    data class ShowError(val message: String) : SearchUiEffect()
}

