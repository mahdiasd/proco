package com.proco.domain.usecase.auth

import androidx.compose.runtime.Stable
import com.proco.domain.fake_data.FakeData
import com.proco.domain.model.user.Country
import com.proco.domain.model.user.Experience
import com.proco.domain.model.user.Expertise
import com.proco.domain.model.user.Gender
import com.proco.domain.model.user.Job
import com.proco.domain.model.user.Skill
import com.proco.domain.model.user.UserType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable

@Serializable
@Stable
data class RegisterParam(
    val name: String = "Mahdi",
    val family: String = "Asd",
    val email: String = "mahdi@yahoo.com",
    val password: String = "12345678",
    val gender: Gender? = Gender.Male,
    val job: Job? = null,
    val expertise: Expertise? = null,
    val experience: Experience? = null,
    val company: String = "Manshour Fanavari",
    val skills: ImmutableList<Skill>? = FakeData.skills().take(4).toImmutableList(),
    val bio: String = "This is test bio from android",
    val country: Country? = null,
    val userType: UserType = UserType.Mentee
)
