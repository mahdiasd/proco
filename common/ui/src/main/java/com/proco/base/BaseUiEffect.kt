package com.proco.base

interface BaseUiEffect : UiEffect {
    data class NetworkError(val networkError: com.proco.domain.model.network.NetworkError) : BaseUiEffect
    data class UiError(val networkError: Int) : BaseUiEffect
}