package com.proco.ui.vector

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.proco.extention.animateClickable
import com.proco.ui.R
import com.proco.ui.text.BodyMediumText

@Composable
fun FailedScreen(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Column(modifier = modifier.animateClickable { onRetry() }) {
        BodyMediumText(text = stringResource(R.string.failed_to_fetch_data_from_server))
    }
}
