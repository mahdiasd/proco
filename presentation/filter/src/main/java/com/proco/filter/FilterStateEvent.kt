package com.proco.filter

import androidx.compose.runtime.Stable
import com.proco.base.UiEffect
import com.proco.base.UiEvent
import com.proco.base.UiState
import com.proco.domain.fake_data.FakeData
import com.proco.domain.model.filter.UserFilter
import com.proco.domain.model.user.Country
import com.proco.domain.model.user.Gender
import com.proco.domain.model.user.Job
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Stable
data class FilterUiState(
    val userFilter: UserFilter = UserFilter(),
    val countries: ImmutableList<Country>? = FakeData.countries(),
    val jobs: ImmutableList<Job>? = FakeData.jobTitles(),
    val gender: ImmutableList<Gender>? = listOf(Gender.Female, Gender.Male, Gender.NonBinary).toImmutableList(),
) : UiState


sealed class FilterUiEvent : UiEvent {
    data class OnJobTitle(val job: Job) : FilterUiEvent()
    data class OnCountry(val country: Country) : FilterUiEvent()
    data class OnGender(val gender: Gender) : FilterUiEvent()
    data object OnSave : FilterUiEvent()
    data object OnClear : FilterUiEvent()
}

sealed class FilterUiEffect : UiEffect {
    data class ShowError(val message: String) : FilterUiEffect()
}
