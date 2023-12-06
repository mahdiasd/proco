package com.proco.base

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.proco.ui.snackbar.FailedSnackBar

@Composable
fun BaseScreen(
    modifier: Modifier,
    alertMessage: String? = null,

    content: @Composable () -> Unit
) {

    Box(modifier = modifier) {
        content()
    }

    if (alertMessage != null) {
        FailedSnackBar(messageText = alertMessage)
    }
}
