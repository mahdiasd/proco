package com.proco.ui.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.proco.extention.withColor
import com.proco.theme.ProcoTheme
import com.proco.utils.ProcoGravity

@Preview
@Composable
private fun Prev() {
    ProcoTheme {
        Column {
            LabelLargeText(text = "this is LabelLargeText preview")
            LabelMediumText(text = "this is LabelMediumText preview")
        }
    }
}

@Composable
fun LabelLargeText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.surface
) {
    Text(modifier = modifier, text = text, style = textStyle.copy(textAlign = textAlign, color = color))
}

@Composable
fun LabelMediumText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.surface
) {
    Text(modifier = modifier, text = text, style = textStyle.copy(textAlign = textAlign, color = color))
}

@Composable
fun LabelMediumText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium.withColor(),
    textAlign: TextAlign = TextAlign.Start,
    icon: Int,
    iconModifier: Modifier = Modifier.size(16.dp),
    iconTint: Color = MaterialTheme.colorScheme.surface,
    iconGravity: ProcoGravity = ProcoGravity.Right,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(4.dp),
    textBottomPadding: Dp = 6.dp
) {

    Row(modifier = modifier, verticalAlignment = verticalAlignment, horizontalArrangement = horizontalArrangement) {
        when (iconGravity) {
            ProcoGravity.Right -> {
                Text(modifier = Modifier.padding(bottom = textBottomPadding), text = text, style = textStyle.copy(textAlign = textAlign))
                Icon(modifier = iconModifier, tint = iconTint, painter = painterResource(id = icon), contentDescription = "")
            }

            ProcoGravity.Left -> {
                Icon(modifier = iconModifier, tint = iconTint, painter = painterResource(id = icon), contentDescription = "")
                Text(modifier = Modifier.padding(bottom = textBottomPadding), text = text, style = textStyle.copy(textAlign = textAlign))
            }
        }
    }

}

