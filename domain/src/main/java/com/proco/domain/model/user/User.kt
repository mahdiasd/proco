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
    val type: UserType,
    val avatar: String = "",
    val job: Job,
    val expertise: Expertise,
    val experience: Int = 0,
    val company: String,
    val skills: ImmutableList<Skill>? = null,
    val bio: String?,
    val price: Int?,
    val country: Country?,
) {
    fun getFullName(): String {
        return "$name $family"
    }
}