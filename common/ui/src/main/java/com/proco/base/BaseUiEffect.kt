package com.proco.base

interface BaseUiEffect : UiEffect {
    data class ShowToast(val message: String)
}