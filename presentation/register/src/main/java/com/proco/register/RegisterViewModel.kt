package com.proco.register

import androidx.lifecycle.viewModelScope
import com.proco.base.BaseViewModel
import com.proco.domain.model.network.DataResult
import com.proco.domain.model.network.getUiMessage
import com.proco.domain.model.user.Skill
import com.proco.domain.usecase.auth.RegisterUseCase
import com.proco.domain.usecase.country.GetCountriesUseCase
import com.proco.domain.usecase.job.GetJobsUseCase
import com.proco.extention.dLog
import com.proco.extention.isNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getJobsUseCase: GetJobsUseCase,
) : BaseViewModel<RegisterUiState, RegisterUiEvent, RegisterUiEffect>() {

    init {
        getJobs()
        getCountries()
    }

    private fun register() {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true) }
            registerUseCase.executeSync(currentState.data).collect {
                setState { currentState.copy(isLoading = false) }
                when (it) {
                    is DataResult.Failure -> setEffect { RegisterUiEffect.ErrorMessage(it.networkError.getUiMessage()) }
                    is DataResult.Success -> setEffect { RegisterUiEffect.SuccessRegister }
                }
            }
        }
    }

    private fun getCountries() {
        viewModelScope.launch {
            getCountriesUseCase.executeSync(Unit).collect {
                when (it) {
                    is DataResult.Failure -> setEffect { RegisterUiEffect.ErrorMessage(it.networkError.getUiMessage()) }
                    is DataResult.Success -> {
                        setState { copy(allCountries = it.data.toImmutableList()) }
                    }
                }
            }
        }
    }

    private fun getJobs() {
        viewModelScope.launch {
            getJobsUseCase.executeSync(Unit).collect {
                when (it) {
                    is DataResult.Failure -> {
                        setEffect { RegisterUiEffect.ErrorMessage(it.networkError.getUiMessage()) }
                    }

                    is DataResult.Success -> setState { copy(allJobs = it.data.toImmutableList()) }
                }
            }
        }
    }

    private fun validate(): Boolean {
        return when {
            currentState.data.name.isEmpty() -> {
                setEffect { RegisterUiEffect.EmptyName }
                false
            }

            currentState.data.family.isEmpty() -> {
                setEffect { RegisterUiEffect.EmptyFamily }
                false
            }

            currentState.data.email.isEmpty() -> {
                setEffect { RegisterUiEffect.EmptyEmail }
                false
            }

            currentState.data.password.isEmpty() -> {
                setEffect { RegisterUiEffect.EmptyPassword }
                false
            }

            currentState.data.job.isNull() -> {
                if (currentState.currentStep == 1) {
                    setEffect { RegisterUiEffect.EmptyJobTitle }
                    false
                } else {
                    true
                }
            }

            else -> return true
        }
    }

    private fun onTyping(type: RegisterTypingType, text: String) {
        when (type) {
            RegisterTypingType.Email -> setState { currentState.copy(data = data.copy(email = text)) }
            RegisterTypingType.Family -> setState { currentState.copy(data = data.copy(family = text)) }
            RegisterTypingType.Name -> setState { currentState.copy(data = data.copy(name = text)) }
            RegisterTypingType.Password -> setState { currentState.copy(data = data.copy(password = text)) }
            RegisterTypingType.Company -> setState { currentState.copy(data = data.copy(company = text)) }
            RegisterTypingType.Bio -> setState { currentState.copy(data = data.copy(bio = text)) }
            RegisterTypingType.Skill -> {}
        }
    }

    override fun createInitialState() = RegisterUiState()

    override fun onTriggerEvent(event: RegisterUiEvent) {
        when (event) {
            is RegisterUiEvent.OnNextStep -> {
                event.index.dLog()
                if (event.index == 3 && validate()) {
                    register()
                } else if ((event.index < currentState.currentStep) || validate())
                    setState { currentState.copy(currentStep = event.index) }
            }

            is RegisterUiEvent.OnRegister -> {}
            is RegisterUiEvent.OnTyping -> onTyping(event.type, event.text)
            is RegisterUiEvent.OnUserType -> setState { currentState.copy(data = data.copy(userType = event.userType)) }
            is RegisterUiEvent.OnGender -> setState { currentState.copy(data = data.copy(gender = event.gender)) }
            is RegisterUiEvent.OnCountry -> setState { currentState.copy(data = data.copy(country = event.country)) }
            is RegisterUiEvent.OnExperience -> setState { currentState.copy(data = data.copy(experience = event.experience)) }
            is RegisterUiEvent.OnExpertise -> setState { currentState.copy(data = data.copy(expertise = event.expertise)) }
            is RegisterUiEvent.OnJobTitle -> setState { currentState.copy(data = data.copy(job = event.job)) }
            is RegisterUiEvent.OnAddSkill -> {
                val temp: MutableList<Skill> = currentState.data.skills?.toMutableList() ?: mutableListOf()
                temp.add(event.skill)
                setState { currentState.copy(data = data.copy(skills = temp.toImmutableList())) }
            }
            is RegisterUiEvent.OnRemoveSkill -> {
                val temp: MutableList<Skill> = currentState.data.skills?.toMutableList() ?: mutableListOf()
                temp.remove(event.skill)
                setState { currentState.copy(data = data.copy(skills = temp.toImmutableList())) }
            }
        }
    }
}
