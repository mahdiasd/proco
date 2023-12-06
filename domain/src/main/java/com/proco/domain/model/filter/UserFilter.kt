package com.proco.domain.model.filter

import com.proco.domain.model.user.Country
import com.proco.domain.model.user.Gender
import com.proco.domain.model.user.Job
import kotlinx.serialization.Serializable

@Serializable
data class UserFilter(val job: Job? = null, val country: Country? = null, val gender: Gender? = null)
