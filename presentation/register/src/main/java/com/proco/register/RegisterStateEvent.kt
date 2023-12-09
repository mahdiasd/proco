package com.proco.register

import androidx.compose.runtime.Stable
import com.proco.base.UiEffect
import com.proco.base.UiEvent
import com.proco.base.UiState
import com.proco.domain.model.user.Country
import com.proco.domain.model.user.Experience
import com.proco.domain.model.user.Expertise
import com.proco.domain.model.user.Gender
import com.proco.domain.model.user.Job
import com.proco.domain.model.user.Skill
import com.proco.domain.model.user.UserType
import com.proco.domain.usecase.auth.RegisterParam
import kotlinx.collections.immutable.ImmutableList

@Stable
data class RegisterUiState(
    val isLoading: Boolean = false,
    val data: RegisterParam = RegisterParam(),
    val currentStep: Int = 0,
    val allSkills: ImmutableList<Skill>? = null,
    val allCountries: ImmutableList<Country>? = null,
    val allJobs: ImmutableList<Job>? = null,
) : UiState

sealed class RegisterUiEvent : UiEvent {
    data object OnRegister : RegisterUiEvent()
    data class OnTyping(val type: RegisterTypingType, val text: String) : RegisterUiEvent()
    data class OnUserType(val userType: UserType) : RegisterUiEvent()
    data class OnGender(val gender: Gender) : RegisterUiEvent()
    data class OnNextStep(val index: Int) : RegisterUiEvent()
    data class OnExpertise(val expertise: Expertise) : RegisterUiEvent()
    data class OnExperience(val experience: Experience) : RegisterUiEvent()
    data class OnJobTitle(val job: Job) : RegisterUiEvent()
    data class OnAddSkill(val skill: Skill) : RegisterUiEvent()
    data class OnRemoveSkill(val skill: Skill) : RegisterUiEvent()
    data class OnCountry(val country: Country) : RegisterUiEvent()
}

sealed class RegisterUiEffect : UiEffect {
    data class ErrorMessage(val message: String) : RegisterUiEffect()
    data object SuccessRegister : RegisterUiEffect()
    data object EmptyName : RegisterUiEffect()
    data object EmptyFamily : RegisterUiEffect()
    data object EmptyEmail : RegisterUiEffect()
    data object EmptyPassword : RegisterUiEffect()
    data object EmptyJobTitle : RegisterUiEffect()
}

sealed class RegisterTypingType {
    data object Name : RegisterTypingType()
    data object Family : RegisterTypingType()
    data object Skill : RegisterTypingType()
    data object Bio : RegisterTypingType()
    data object Email : RegisterTypingType()
    data object Password : RegisterTypingType()
    data object Company : RegisterTypingType()
}