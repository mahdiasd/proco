package com.proco.search

import androidx.lifecycle.viewModelScope
import com.proco.base.BaseViewModel
import com.proco.domain.model.network.DataResult
import com.proco.domain.model.network.getUiMessage
import com.proco.domain.usecase.GetMentorListUseCase
import com.proco.domain.usecase.filter.GetUserFilterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: GetMentorListUseCase,
    private val getUserFilterUseCase: GetUserFilterUseCase
) : BaseViewModel<SearchViewState, SearchViewEvent, SearchUiEffect>() {

    init {
        getUserFilter()
    }

    private fun getUsers() {
        viewModelScope.launch {
            useCase.executeSync(
                GetMentorListUseCase.GetMentorListParam(
                    search = currentState.searchText,
                    jobTitle = currentState.userFilter?.job?.name,
                    country = currentState.userFilter?.country?.code,
                    page = currentState.page
                )
            ).collect {
                when (it) {
                    is DataResult.Success -> {
                        setState { currentState.copy(isLoading = false, data = it.data.toImmutableList(), alertMessage = null) }
                    }

                    is DataResult.Failure -> {
                        setEffect { SearchUiEffect.ShowError(it.errorEntity.getUiMessage()) }
                    }
                }
            }
        }
    }

    private fun getUserFilter() {
        viewModelScope.launch {
            getUserFilterUseCase.executeSync(Unit).collect {
                setState { currentState.copy(userFilter = it) }
                getUsers()
            }
        }
    }

    override fun createInitialState() = SearchViewState()

    override fun onTriggerEvent(event: SearchViewEvent) {
        when (event) {
            is SearchViewEvent.OnTyping -> {
                setState { currentState.copy(searchText = event.text) }
                getUsers()
            }
        }
    }


}
