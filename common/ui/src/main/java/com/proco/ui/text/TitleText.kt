package com.proco.ui.text

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun Prev() {
    TitleLargeText(text = "this is TitleLargeText preview")
    TitleMediumText(text = "this is TitleMediumText preview")
}

@Composable
fun TitleLargeText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    maxLines: Int = 1,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = MaterialTheme.colorScheme.surface,
) {
    Text(modifier = modifier, text = text, style = textStyle.copy(textAlign = textAlign, color = color), maxLines = maxLines)
}

@Composable
fun TitleMediumText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center),
    maxLines: Int = 1,
    color: Color = MaterialTheme.colorScheme.surface,
    forceVerticalCenter: Boolean = false,
) {
    if (forceVerticalCenter) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text(text = text, style = textStyle.copy(color = color), maxLines = maxLines)
        }
    } else {
        Text(modifier = modifier, text = text, style = textStyle.copy(color = color), maxLines = maxLines)
    }
}