package com.proco.data.model

import com.proco.domain.model.user.Expertise
import com.proco.domain.model.user.Skill
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val sex: String,
    val type: String,
    val avatar: String?,
    val companyName: String?,
    val education: String?,
    val experience: Int,
    val bio: String?,
    val cost: Int?,
    val country: String?,
    val job: String?,
    val skills: List<Skill>?,
    val expertises: List<Expertise>?,
)
