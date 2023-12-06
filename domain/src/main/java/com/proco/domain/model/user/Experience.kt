package com.proco.domain.model.user

sealed class Experience(val index: Int, val title: String) {
    data object Junior : Experience(0, "1 - 3")
    data object Mid : Experience(1, "3 - 5")
    data object Senior : Experience(1, "5 - 8")
    data object Expert : Experience(1, "8+ ")
}