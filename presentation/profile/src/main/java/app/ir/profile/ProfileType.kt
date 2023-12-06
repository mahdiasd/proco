package app.ir.profile

sealed interface ProfileType {
    data object Self : ProfileType
    data object Public : ProfileType
}