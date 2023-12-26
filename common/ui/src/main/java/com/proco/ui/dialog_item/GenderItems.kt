package com.proco.ui.dialog_item

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.proco.domain.model.user.Gender
import com.proco.extention.animateClickable
import com.proco.extention.uiName
import com.proco.ui.text.BodyLargeText

@Composable
fun GenderItems(gender: Gender, onClick: () -> Unit) {
    BodyLargeText(
        modifier = Modifier
            .animateClickable { onClick() }
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        text = stringResource(id = gender.uiName()),
        textAlign = TextAlign.Center
    )
}
