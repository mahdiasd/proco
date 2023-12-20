package com.proco.ui.dialog_item

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.proco.domain.model.user.Expertise
import com.proco.extention.animateClickable
import com.proco.ui.R
import com.proco.ui.text.BodyLargeText
import com.proco.ui.text.BodyMediumText
import com.proco.utils.ProcoGravity

@Composable
fun ExpertiseListItem(item: Expertise, onClick: () -> Unit) {
    BodyLargeText(
        modifier = Modifier
            .animateClickable { onClick() }
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        text = item.name,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ExpertiseItem(item: Expertise, onRemove: () -> Unit) {
    BodyMediumText(
        modifier = Modifier
            .padding(top = 8.dp , end = 8.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.small)
            .padding(8.dp)
            .animateClickable { onRemove() },
        text = item.name,
        textAlign = TextAlign.Center,
        icon = R.drawable.ic_close,
        iconGravity = ProcoGravity.Left,
    )
}
