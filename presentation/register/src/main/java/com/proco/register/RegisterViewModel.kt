package com.proco.register

import androidx.lifecycle.viewModelScope
import com.proco.base.BaseViewModel
import com.proco.base.UiMessage
import com.proco.domain.model.network.DataResult
import com.proco.domain.model.user.Expertise
import com.proco.domain.model.user.Skill
import com.proco.domain.usecase.auth.RegisterUseCase
import com.proco.domain.usecase.country.GetCountriesUseCase
import com.proco.domain.usecase.job.GetJobsUseCase
import com.proco.extention.dLog
import com.proco.extention.isNull
import com.proco.extention.safeAdd
import com.proco.ui.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getJobsUseCase: GetJobsUseCase,
) : BaseViewModel<RegisterUiState, RegisterUiEvent>() {

    init {
        getJobs()
        getCountries()
    }
    private fun register() {
        setState { currentState.copy(isLoading = true, uiMessage = null) }
        viewModelScope.launch {
            registerUseCase.executeSync(currentState.data).collect {
                setState { currentState.copy(isLoading = false) }
                when (it) {
                    is DataResult.Failure -> setState { currentState.copy(uiMessage = UiMessage.Network(it.networkError)) }
                    is DataResult.Success -> setState { currentState.copy(isRegistered = true) }
                }
            }
        }
    }


    private fun getCountries() {
        viewModelScope.launch {
            getCountriesUseCase.executeSync(Unit).collect {
                when (it) {
                    is DataResult.Failure -> setState { currentState.copy(uiMessage = UiMessage.Network(it.networkError)) }
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
                    is DataResult.Failure -> setState { currentState.copy(uiMessage = UiMessage.Network(it.networkError)) }
                    is DataResult.Success -> setState { copy(allJobs = it.data.toImmutableList()) }
                }
            }
        }
    }

    private fun validate(): Boolean {
        return when {
            currentState.data.name.isEmpty() -> {
                setState { currentState.copy(uiMessage = UiMessage.System(R.string.empty_name)) }
                false
            }

            currentState.data.family.isEmpty() -> {
                setState { currentState.copy(uiMessage = UiMessage.System(R.string.empty_family)) }
                false
            }

            currentState.data.email.isEmpty() -> {
                setState { currentState.copy(uiMessage = UiMessage.System(R.string.empty_email)) }
                false
            }

            currentState.data.password.isEmpty() -> {
                setState { currentState.copy(uiMessage = UiMessage.System(R.string.empty_password)) }
                false
            }

            currentState.data.job.isNull() -> {
                if (currentState.currentStep == 1) {
                    setState { currentState.copy(uiMessage = UiMessage.System(R.string.empty_job_title)) }
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

            is RegisterUiEvent.OnEducation -> {
                setState { currentState.copy(data = data.copy(education = event.education)) }
            }

            is RegisterUiEvent.OnTyping -> {
                onTyping(event.type, event.text)
            }
            is RegisterUiEvent.OnUserType -> {
                setState { currentState.copy(data = data.copy(userType = event.userType)) }
            }
            is RegisterUiEvent.OnGender -> {
                setState { currentState.copy(data = data.copy(gender = event.gender)) }
            }
            is RegisterUiEvent.OnCountry -> {
                setState { currentState.copy(data = data.copy(country = event.country)) }
            }
            is RegisterUiEvent.OnExperience -> {
                setState { currentState.copy(data = data.copy(experience = event.experience)) }
            }
            is RegisterUiEvent.OnExpertise -> {
                val temp: MutableList<Expertise> = currentState.data.expertises?.toMutableList() ?: mutableListOf()
                temp.takeIf { currentState.data.expertises?.firstOrNull { it.id == event.expertise.id } == null }.also {
                    setState { currentState.copy(data = data.copy(expertises = temp.safeAdd(event.expertise).toImmutableList())) }
                }
            }

            is RegisterUiEvent.OnRemoveExpertise -> {
                val temp: MutableList<Expertise> = currentState.data.expertises?.toMutableList() ?: mutableListOf()
                temp.remove(event.expertise)
                setState { currentState.copy(data = data.copy(expertises = temp.toImmutableList())) }
            }

            is RegisterUiEvent.OnJobTitle -> {
                setState { currentState.copy(data = data.copy(job = event.job)) }
            }
            is RegisterUiEvent.OnAddSkill -> {
                val temp: MutableList<Skill> = currentState.data.skills?.toMutableList() ?: mutableListOf()
                temp.safeAdd(event.skill)
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
