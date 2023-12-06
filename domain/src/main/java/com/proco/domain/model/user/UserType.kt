package com.proco.domain.model.user

sealed class UserType {
    data object Mentee : UserType()
    data object Mentor : UserType()
}