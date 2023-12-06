package com.proco.ui.toolbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.proco.extention.animateClickable
import com.proco.ui.R
import com.proco.ui.text.TitleMediumText

@Composable
fun ProcoToolbar(
    title: Int? = null,
    leftIcon: Int? = R.drawable.ic_back,
    leftIconSize: Dp = 32.dp,
    rightIcon: Int? = null,
    rightIconSize: Dp = 32.dp,
    tint: Color = MaterialTheme.colorScheme.surface,
    onLeft: () -> Unit = {},
    onRight: () -> Unit = {},
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (leftIcon != null)
            Icon(
                modifier = Modifier
                    .size(rightIconSize)
                    .animateClickable(onLeft)
                    .padding(4.dp),
                painter = painterResource(id = leftIcon), contentDescription = "",
                tint = tint
            )

        if (title != null)
            TitleMediumText(text = stringResource(id = title))

        if (rightIcon != null)
            Icon(
                modifier = Modifier
                    .size(leftIconSize)
                    .animateClickable(onRight)
                    .padding(4.dp),
                painter = painterResource(id = rightIcon), contentDescription = "",
                tint = tint
            )

    }
}