package com.proco.base

import com.proco.domain.model.network.NetworkError

interface UiMessage : UiEffect {
    data class Network(val networkError: NetworkError, val uiMessageType: UiMessageType = UiMessageType.Failure) : UiMessage
    data class System(val error: Int, val uiMessageType: UiMessageType = UiMessageType.Failure) : UiMessage
}

enum class UiMessageType { Success, Failure }