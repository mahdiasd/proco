package com.proco.ui.vector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.proco.extention.animateClickable
import com.proco.ui.R
import com.proco.ui.text.BodyMediumText

@Composable
fun FailedScreen(
    modifier: Modifier = Modifier,
    descriptionText: Int = R.string.failed_to_fetch_data_from_server,
    actionText: Int = R.string.try_again,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.animateClickable { onRetry() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BodyMediumText(text = stringResource(id = descriptionText))

        BodyMediumText(text = stringResource(id = actionText))
    }
}
