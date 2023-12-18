package com.proco.filter

import androidx.lifecycle.viewModelScope
import com.proco.base.BaseViewModel
import com.proco.base.UiMessage
import com.proco.domain.model.filter.UserFilter
import com.proco.domain.model.network.DataResult
import com.proco.domain.usecase.country.GetCountriesUseCase
import com.proco.domain.usecase.filter.GetUserFilterUseCase
import com.proco.domain.usecase.filter.SaveUserFilterUseCase
import com.proco.domain.usecase.job.GetJobsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val getUseCase: GetUserFilterUseCase,
    private val saveUseCase: SaveUserFilterUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getJobsUseCase: GetJobsUseCase
) : BaseViewModel<FilterUiState, FilterUiEvent>() {

    init {
        read()
        getJobs()
        getCountries()
    }

    private fun save() {
        viewModelScope.launch {
            saveUseCase.executeSync(currentState.userFilter)
        }
    }

    private fun getCountries() {
        viewModelScope.launch {
            getCountriesUseCase.executeSync(Unit).collect {
                when (it) {
                    is DataResult.Failure -> setState { currentState.copy(uiMessage = UiMessage.Network(it.networkError)) }
                    is DataResult.Success -> {
                        setState { copy(countries = it.data.toImmutableList()) }
                    }
                }
            }
        }
    }

    private fun getJobs() {
        viewModelScope.launch {
            getJobsUseCase.executeSync(Unit).collect {
                when (it) {
                    is DataResult.Failure -> setState { currentState.copy(uiMessage = UiMessage.Network(it.networkError)) }
                    is DataResult.Success -> setState { copy(jobs = it.data.toImmutableList()) }
                }
            }
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
