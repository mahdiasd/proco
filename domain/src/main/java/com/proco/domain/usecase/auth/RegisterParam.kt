package com.proco.domain.usecase.auth

import androidx.compose.runtime.Stable
import com.proco.app.data.model.Skill
import com.proco.domain.model.user.Country
import com.proco.domain.model.user.Expertise
import com.proco.domain.model.user.Gender
import com.proco.domain.model.user.Job
import com.proco.domain.model.user.UserType
import kotlinx.collections.immutable.ImmutableList

@Stable
data class RegisterParam(
    val name: String = "",
    val family: String = "",
    val email: String = "",
    val password: String = "",
    val gender: Gender? = null,
    val job: Job? = null,
    val expertise: Expertise? = null,
    val experience: Int = 0,
    val company: String = "",
    val skills: ImmutableList<Skill>? = null,
    val bio: String = "",
    val country: Country? = null,
    val userType: UserType = UserType.Mentee
)
