package com.proco.base

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.proco.domain.model.network.getUiMessage
import com.proco.ui.snackbar.FailedSnackBar
import com.proco.ui.snackbar.SuccessSnackBar

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
                when(uiMessage.uiMessageType){
                    UiMessageType.Success -> SuccessSnackBar(messageText = uiMessage.networkError.getUiMessage())
                    UiMessageType.Failure -> FailedSnackBar(messageText = uiMessage.networkError.getUiMessage())
                }
            }

            is UiMessage.System -> {
                when(uiMessage.uiMessageType){
                    UiMessageType.Success -> SuccessSnackBar(messageText = stringResource(id = uiMessage.error))
                    UiMessageType.Failure -> FailedSnackBar(messageText = stringResource(id = uiMessage.error))
                }

            }
        }

    }
}
