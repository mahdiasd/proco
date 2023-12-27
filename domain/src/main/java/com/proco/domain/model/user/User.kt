package com.proco.domain.model.user

import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable

/**
 * The `User` class represents user with all properties.
 */
@Serializable
data class User(
    val id: Int,
    val name: String,
    val family: String,
    val email: String,
    val type: UserType,
    val avatar: String,
    val gender: Gender,
    val job: String,
    val experience: Int = 0,
    val company: String,
    val education: String,
    val skills: ImmutableList<Skill>?,
    val expertises: ImmutableList<Expertise>?,
    val bio: String?,
    val cost: Int,
    val country: String,
) {
    fun getFullName(): String {
        return "$name $family"
    }
}