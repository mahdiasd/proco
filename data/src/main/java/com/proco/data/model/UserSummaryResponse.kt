package com.proco.data.model

import com.proco.domain.model.user.Country
import com.proco.domain.model.user.Expertise
import com.proco.domain.model.user.Job
import com.proco.domain.model.user.Skill
import kotlinx.collections.immutable.ImmutableList
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
    val cost: Int,
    val job: Job,
    val country: Country?,
    val expertise: ImmutableList<Expertise>,
    val skills: ImmutableList<Skill>?

)