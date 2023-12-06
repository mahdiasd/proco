package com.proco.ui.vector

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.proco.ui.R
import com.proco.ui.text.BodyMediumText

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        BodyMediumText(text = stringResource(R.string.no_data_to_show))
    }
}

