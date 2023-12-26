package com.proco.search

import androidx.compose.runtime.Stable
import com.proco.base.BaseUiState
import com.proco.base.UiEffect
import com.proco.base.UiEvent
import com.proco.base.UiMessage
import com.proco.domain.model.filter.UserFilter
import com.proco.domain.model.user.UserSummary
import com.proco.utils.MyConstant
import kotlinx.collections.immutable.ImmutableList

@Stable
data class SearchViewState(
    override val isLoading: Boolean = false,
    override val data: ImmutableList<UserSummary>? = null,
    override val uiMessage: UiMessage? = null,
    val searchText: String = "",
    val currentStep: Int = 0,
    val userFilter: UserFilter? = UserFilter(),
    val page: Int = MyConstant.defaultPageNumber,
    val isLoadMore: Boolean = page > MyConstant.defaultPageNumber && isLoading
) : BaseUiState<ImmutableList<UserSummary>?>


sealed class SearchViewEvent : UiEvent {
    data class OnTyping(val text: String) : SearchViewEvent()
    data object OnRefresh : SearchViewEvent()
    data object OnNextPage : SearchViewEvent()
}

sealed class SearchUiEffect : UiEffect {
    data class ShowError(val message: String) : SearchUiEffect()
}

