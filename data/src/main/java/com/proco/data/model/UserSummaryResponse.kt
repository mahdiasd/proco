package com.proco.data.model

import com.proco.domain.model.user.Expertise
import com.proco.domain.model.user.Skill
import kotlinx.serialization.Serializable

@Serializable
data class UserSummaryResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatar: String?,
    val sex: String,
    val type: String,
    val cost: Int?,
    val job: String?,
    val country: String,
    val expertises: List<Expertise>,
    val skills: List<Skill>?

)