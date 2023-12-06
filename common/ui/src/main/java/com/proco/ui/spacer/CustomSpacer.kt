package com.proco.ui.spacer

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomSpacer(height: Dp = 16.dp, width: Dp = 0.dp) {
    androidx.compose.foundation.layout.Spacer(
        modifier = Modifier
            .height(height)
            .width(width)
    )
}