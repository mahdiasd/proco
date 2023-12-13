package com.proco.ui.dash_line

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import com.proco.theme.gray

@Composable
fun HorizontalDashLine(modifier: Modifier) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f)
    Canvas(
        modifier = modifier
    ) {
        drawLine(
            color = gray,
            strokeWidth = 5f,
            start = Offset(20f, 0f),
            end = Offset(size.width - 20, 0f),
            pathEffect = pathEffect
        )
    }
}
