package com.proco.ui.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.proco.extention.animateClickable
import com.proco.extention.coloredShadow
import com.proco.theme.ProcoTheme
import com.proco.theme.white
import com.proco.ui.R
import com.proco.ui.loading.DotsPulsing
import com.proco.ui.text.LabelMediumText
import com.proco.ui.text.TitleLargeText
import com.proco.utils.ProcoGravity

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Prev() {
    ProcoTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {
            ProcoButton(text = "Save", onClick = {})
            ProcoSmallButton(text = "Save", onClick = {})
        }
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

@Composable
fun ProcoSmallButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge.copy(textAlign = TextAlign.Center),
    isLoading: Boolean = false,
    onClick: () -> Unit,
    icon: Int? = null,
    iconModifier: Modifier = Modifier.size(16.dp),
    iconTint: Color = white,
    textBottomPadding: Dp = 2.dp
) {
    if (isLoading)
        DotsPulsing()
    else {
        if (icon == null) {
            LabelMediumText(
                modifier = modifier
                    .animateClickable(onClick)
                    .wrapContentSize()
                    .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                text = text,
                textStyle = textStyle,
                textAlign = TextAlign.Center,
                color = white
            )
        } else {
            LabelMediumText(
                modifier = modifier
                    .animateClickable(onClick)
                    .wrapContentSize()
                    .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                text = stringResource(id = R.string.add_time),
                textStyle = textStyle.copy(color = white),
                textAlign = TextAlign.Center,
                icon = icon,
                iconTint = iconTint,
                iconGravity = ProcoGravity.Left,
                iconModifier = iconModifier,
                textBottomPadding = textBottomPadding,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            )
        }

    }

}
