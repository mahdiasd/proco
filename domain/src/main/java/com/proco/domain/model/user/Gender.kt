package com.proco.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
sealed class Gender {
    data object Male : Gender()
    data object Female : Gender()
    data object NonBinary : Gender()
}
