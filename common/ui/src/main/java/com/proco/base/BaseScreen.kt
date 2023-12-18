package com.proco.base

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.proco.domain.model.network.getUiMessage
import com.proco.ui.snackbar.FailedSnackBar

@Composable
fun BaseScreen(
    modifier: Modifier,
    uiMessage: UiMessage? = null,

    content: @Composable () -> Unit
) {

    Box(modifier = modifier) {
        content()
    }

    if (uiMessage != null) {
        when (uiMessage) {
            is UiMessage.Network -> {
                FailedSnackBar(messageText = uiMessage.networkError.getUiMessage())
            }

            is UiMessage.System -> {
                FailedSnackBar(messageText = stringResource(id = uiMessage.error))
            }
        }

    }
}
