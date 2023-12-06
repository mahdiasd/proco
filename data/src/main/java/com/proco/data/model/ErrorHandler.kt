package com.proco.data.model

import com.proco.domain.model.network.ErrorEntity

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}