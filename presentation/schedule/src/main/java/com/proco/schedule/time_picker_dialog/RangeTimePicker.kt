package com.proco.schedule.time_picker_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.proco.domain.model.schedule.HourRange
import com.proco.extention.animateClickable
import com.proco.extention.toLocalTime
import com.proco.theme.ProcoTheme
import com.proco.theme.gray
import com.proco.ui.R
import com.proco.ui.spacer.CustomSpacer
import com.proco.ui.text.BodyMediumText
import com.proco.ui.text.TitleMediumText
import com.proco.ui.time_picker.ProcoTimeInput
import com.proco.utils.DateUtils
import com.proco.utils.MyConstant
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalTime

@Preview(showBackground = true)
@Composable
private fun LightPreview() {
    ProcoTheme {
        RangeTimePicker(null, { _, _ -> }, {})
    }
}

@Preview(showBackground = true)
@Composable
private fun DarkPreview() {
    ProcoTheme(true) {
        RangeTimePicker(null, { _, _ -> }, {})
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RangeTimePicker(
    hours: ImmutableList<HourRange>?,
    onSave: (LocalTime, LocalTime) -> Unit,
    onDismiss: () -> Unit
) {
    var startTime by remember { mutableStateOf(hours?.lastOrNull()?.end?.toLocalTime()?.plusMinutes(1) ?: LocalTime.now()) }
    var endTime by remember(startTime) { mutableStateOf(DateUtils.add15Minutes(startTime.hour, startTime.minute)) }

    val minTime by remember(startTime) {
        mutableStateOf(DateUtils.add15Minutes(startTime.hour, startTime.minute))
    }


    val errorMessage: Int? by remember(hours, startTime, endTime) {
        derivedStateOf {
            when {
                endTime.minusMinutes((MyConstant.minRangeTime - 1).toLong()).isBefore(startTime) -> R.string.min_range_of_time_error
                hours?.any { DateUtils.isOverLapTime(it.start.toLocalTime(), it.end.toLocalTime(), startTime, endTime) } == true -> {
                    R.string.overlap_range_time
                }

                else -> null
            }
        }
    }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.tertiary, MaterialTheme.shapes.medium)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BodyMediumText(text = errorMessage?.let { stringResource(id = it) } ?: "", color = MaterialTheme.colorScheme.error)

            TitleMediumText(text = stringResource(id = R.string.enter_start_time))

            ProcoTimeInput(minTime = startTime, onTimeSelected = { startTime = it })
            Divider()

            TitleMediumText(text = stringResource(id = R.string.enter_end_time))
            ProcoTimeInput(minTime = minTime, lastFocusDirection = FocusDirection.Enter, onTimeSelected = { endTime = it })
            CustomSpacer(8.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TitleMediumText(modifier = Modifier.animateClickable(onDismiss), text = stringResource(id = R.string.cancel), color = gray)
                Spacer(modifier = Modifier.width(16.dp))
                TitleMediumText(
                    modifier = Modifier.animateClickable {
                        if (errorMessage == null) onSave(startTime, endTime)
                    },
                    text = stringResource(id = R.string.save),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

    }
}
