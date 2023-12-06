package com.proco.filter

import androidx.lifecycle.viewModelScope
import com.proco.base.BaseViewModel
import com.proco.domain.model.filter.UserFilter
import com.proco.domain.usecase.filter.GetUserFilterUseCase
import com.proco.domain.usecase.filter.SaveUserFilterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val getUseCase: GetUserFilterUseCase,
    private val saveUseCase: SaveUserFilterUseCase
) : BaseViewModel<FilterUiState, FilterUiEvent, FilterUiEffect>() {

    init {
        read()
    }

    private fun save() {
        viewModelScope.launch {
            saveUseCase.executeSync(currentState.userFilter)
        }
    }

    private fun read() {
        viewModelScope.launch {
            getUseCase.executeSync(Unit).collect {
                setState { currentState.copy(userFilter = it ?: UserFilter()) }
            }
        }
    }

    override fun createInitialState() = FilterUiState()

    override fun onTriggerEvent(event: FilterUiEvent) {
        when (event) {
            is FilterUiEvent.OnCountry -> setState { currentState.copy(userFilter = userFilter.copy(country = event.country)) }
            is FilterUiEvent.OnGender -> setState { currentState.copy(userFilter = userFilter.copy(gender = event.gender)) }
            is FilterUiEvent.OnJobTitle -> setState { currentState.copy(userFilter = userFilter.copy(job = event.job)) }
            FilterUiEvent.OnClear -> setState { currentState.copy(userFilter = UserFilter()) }
            FilterUiEvent.OnSave -> save()
        }
    }

}
