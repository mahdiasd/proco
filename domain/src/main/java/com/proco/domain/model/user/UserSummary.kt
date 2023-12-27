package com.proco.domain.model.user

import kotlinx.collections.immutable.ImmutableList

/**
 * The `UserSummary` class represents user with compact properties
 * Use for showing in a list for compact view.
 */
data class UserSummary(
    val id: Int,
    val name: String,
    val avatar: String,
    val family: String,
    val gender: Gender,
    val email: String,
    val type: UserType,
    val cost: Int,
    val job: String,
    val country: String?,
    val expertises: ImmutableList<Expertise>,
    val skills: ImmutableList<Skill>?
)
