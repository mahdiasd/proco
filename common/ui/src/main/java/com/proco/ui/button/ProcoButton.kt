package com.proco.ui.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proco.extention.coloredShadow
import com.proco.theme.white
import com.proco.ui.loading.DotsPulsing
import com.proco.ui.text.TitleLargeText

@Preview
@Composable
private fun Prev() {
    MaterialTheme {
        ProcoButton(text = "Login", onClick = {})
    }
}

@Composable
fun ProcoButton(
    modifier: Modifier = Modifier,
    fillMaxWidth: Boolean = true,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .widthIn(max = 320.dp)
            .then(
                if (fillMaxWidth) Modifier.fillMaxWidth()
                else Modifier
            )
            .coloredShadow(MaterialTheme.colorScheme.primary, borderRadius = 15.dp, offsetY = 10.dp)
            .shadow(elevation = 10.dp, shape = MaterialTheme.shapes.small, spotColor = MaterialTheme.colorScheme.primary)
            .background(color = MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.small),
        onClick = onClick
    ) {
        if (isLoading)
            DotsPulsing()
        else
            TitleLargeText(modifier = Modifier, text = text, textStyle = textStyle, maxLines = 1, color = white)
    }
}
