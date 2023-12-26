package com.proco.domain.model.user

import kotlinx.serialization.Serializable

/**
 * The UserCache class represents a user with only the necessary properties.
 * It is used to save a cached version of the user that can be retrieved when
 * the app needs the userType or other core data.
 */
@Serializable
data class UserCache(
    val id: Int,
    val name: String,
    val family: String,
    val type: UserType,
    val email: String,
    val cost: Int,
) {
    fun getFullName(): String {
        return "$name $family"
    }
}