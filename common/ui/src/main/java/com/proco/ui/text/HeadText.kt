package com.proco.ui.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.proco.extention.withColor

@Preview(showBackground = true)
@Composable
private fun Prev() {
    HeadLargeText(text = "this is HeadLargeText preview")
    HeadMediumText(text = "this is HeadMediumText preview")
}

@Composable
fun HeadLargeText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge.withColor(MaterialTheme.colorScheme.primary).copy(textAlign = TextAlign.Center),
    maxLines: Int = 1,
) {
    Text(modifier = modifier, text = text, style = textStyle, maxLines = maxLines)
}@Composable
fun HeadMediumText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge.withColor(MaterialTheme.colorScheme.primary).copy(textAlign = TextAlign.Center),
    maxLines: Int = 1,
) {
    Text(modifier = modifier, text = text, style = textStyle, maxLines = maxLines)
}