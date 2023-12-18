package com.proco.base

interface BaseUiState<T> : UiState {
    val isLoading: Boolean
    val data: T?
    val uiMessage: UiMessage?
}