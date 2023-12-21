package com.proco.ui.hour_range

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.proco.domain.model.schedule.HourRange
import com.proco.extention.animateClickable
import com.proco.extention.toHour
import com.proco.extention.toLocalDateTime
import com.proco.extention.toMinute
import com.proco.extention.withColor
import com.proco.ui.R
import com.proco.ui.text.AutoSizeText
import com.proco.ui.text.BodyMediumText
import com.proco.utils.ProcoGravity

@Composable
fun HourRangeEditableItem(range: HourRange, onRemove: () -> Unit) {
    BodyMediumText(
        modifier = Modifier
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.secondary, shape = MaterialTheme.shapes.small)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.small)
            .padding(8.dp),
        text = "${range.start.toLocalDateTime().hour.toHour()}:${range.start.toLocalDateTime().minute.toMinute()} -- ${range.end.toLocalDateTime().hour.toHour()}:${range.end.toLocalDateTime().minute.toMinute()}",
        textStyle = MaterialTheme.typography.bodyMedium.withColor(),
        icon = R.drawable.ic_close,
        textBottomPadding = 3.dp,
        iconGravity = ProcoGravity.Left,
        iconModifier = Modifier
            .size(16.dp)
            .animateClickable { onRemove() }
    )
}

@Composable
fun HourRangeItem(modifier: Modifier,range: HourRange, onClick: () -> Unit) {
    AutoSizeText(
        modifier = modifier
            .padding(4.dp)
            .animateClickable(onClick)
            .background(MaterialTheme.colorScheme.secondary, shape = MaterialTheme.shapes.small)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.small)
            .padding(8.dp),
        text = "${range.start.toLocalDateTime().hour.toHour()}:${range.start.toLocalDateTime().minute.toMinute()} -- ${range.end.toLocalDateTime().hour.toHour()}:${range.end.toLocalDateTime().minute.toMinute()}",
    )
}
