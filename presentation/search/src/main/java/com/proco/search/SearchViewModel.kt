package com.proco.search

import androidx.lifecycle.viewModelScope
import com.proco.base.BaseViewModel
import com.proco.base.UiMessage
import com.proco.domain.model.network.DataResult
import com.proco.domain.usecase.GetMentorListUseCase
import com.proco.domain.usecase.filter.GetUserFilterUseCase
import com.proco.extention.dLog
import com.proco.utils.MyConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: GetMentorListUseCase,
    private val getUserFilterUseCase: GetUserFilterUseCase
) : BaseViewModel<SearchViewState, SearchViewEvent>() {

    init {
        getUserFilter()
    }

    private fun getUsers() {
        setLoadingState()
        viewModelScope.launch {
            useCase.executeSync(
                GetMentorListUseCase.GetMentorListParam(
                    search = currentState.searchText,
                    jobTitle = currentState.userFilter?.job?.name,
                    country = currentState.userFilter?.country?.name,
                    page = currentState.page
                )
            ).collect {
                when (it) {
                    is DataResult.Success -> {
                        setState { currentState.copy(isLoading = false, isLoadMore = false, uiMessage = null) }
                        if (currentState.page == MyConstant.defaultPageNumber)
                            setState { currentState.copy(isLoading = false, data = it.data.toImmutableList(), uiMessage = null) }
                        else
                            setState { currentState.copy(isLoading = false, data = data?.toMutableList()?.apply { this.addAll(it.data) }?.toImmutableList(), uiMessage = null) }
                    }

                    is DataResult.Failure -> {
                        setState { currentState.copy(isLoading = false, isLoadMore = false, uiMessage = UiMessage.Network(it.networkError)) }
                    }
                }
            }
        }
    }

    private fun getUserFilter() {
        viewModelScope.launch {
            getUserFilterUseCase.executeSync(Unit).collect {
                it.dLog("userFilter: ")
                setState { currentState.copy(userFilter = it, page = MyConstant.defaultPageNumber) }
                getUsers()
            }
        }
    }

    private fun setLoadingState() {
        if (currentState.page == MyConstant.defaultPageNumber)
            setState { currentState.copy(isLoading = true, uiMessage = null) }
        else
            setState { currentState.copy(isLoadMore = true, uiMessage = null) }
    }

    override fun createInitialState() = SearchViewState()

    override fun onTriggerEvent(event: SearchViewEvent) {
        when (event) {
            is SearchViewEvent.OnTyping -> {
                setState { currentState.copy(searchText = event.text) }
                getUsers()
            }

            is SearchViewEvent.OnNextPage -> {
                setState { currentState.copy(page = page + 1) }
                getUsers()
            }

            is SearchViewEvent.OnRefresh -> {
                setState { currentState.copy(page = MyConstant.defaultPageNumber) }
                getUsers()
            }
        }
    }


}
