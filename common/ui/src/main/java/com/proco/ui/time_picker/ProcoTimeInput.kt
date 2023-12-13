package com.proco.ui.time_picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proco.extention.dLog
import com.proco.extention.toHour
import com.proco.extention.toMinute
import com.proco.theme.gray
import com.proco.ui.R
import com.proco.ui.spacer.CustomSpacer
import com.proco.ui.text.LabelMediumText
import com.proco.ui.text.TitleLargeText
import com.proco.ui.text_field.ProcoTextField
import kotlinx.coroutines.launch
import java.time.LocalTime

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProcoTimeInput(
    minTime: LocalTime,
    lastFocusDirection: FocusDirection = FocusDirection.Next,
    onTimeSelected: (LocalTime) -> Unit
) {

    var hourText by remember(minTime.hour) { mutableStateOf(minTime.hour.toString()) }
    var minuteText by remember(minTime.minute) { mutableStateOf(minTime.minute.toString()) }

    var hourTextHint by remember { mutableStateOf(hourText) }
    var minuteTextHint by remember { mutableStateOf(minuteText) }

    var focusOnHour by remember { mutableStateOf(false) }
    var focusOnMinute by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val cScope = rememberCoroutineScope()

    LaunchedEffect(key1 = focusOnHour) {
        if (focusOnHour) {
            hourTextHint = hourText
            hourText = ""
        } else if (hourText.isEmpty()) {
            hourText = hourTextHint
        }
    }
    LaunchedEffect(key1 = focusOnMinute) {
        if (focusOnMinute) {
            minuteTextHint = minuteText
            minuteText = ""
        } else if (minuteText.isEmpty()) {
            minuteText = minuteTextHint
        }
    }

    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Column {
            ProcoTextField(
                modifier = Modifier
                    .size(85.dp, 65.dp)
                    .onFocusChanged { focusOnHour = it.hasFocus },
                value = hourText,
                textStyle = MaterialTheme.typography.titleLarge.copy(fontSize = 28.sp),
                textAlign = TextAlign.Center,
                hintTextStyle = MaterialTheme.typography.labelLarge.copy(textAlign = TextAlign.Center, color = gray),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.surface,
                    unfocusedTextColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.scrim,
                    focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.scrim,
                ),
                maxLines = 1,
                actionNext = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = {
                    it.dLog("it")
                    hourText = it.toHour()
                    hourText.dLog("hourText")
                    onTimeSelected(LocalTime.of(hourText.toHour().toInt(), minuteText.toMinute().toInt()))
                    if (hourText.length == 2)
                        cScope.launch { focusManager.moveFocus(FocusDirection.Next) }
                },
                hint = hourTextHint
            )
            CustomSpacer(height = 8.dp)
            LabelMediumText(text = stringResource(id = R.string.hour))
        }

        TitleLargeText(text = ":", textStyle = MaterialTheme.typography.headlineMedium.copy(fontSize = 45.sp))

        Column {
            ProcoTextField(
                modifier = Modifier
                    .onFocusChanged {
                        focusOnMinute = it.hasFocus
                    }
                    .size(85.dp, 65.dp),
                value = minuteText,
                maxLines = 1,
                actionNext = false,
                hintTextStyle = MaterialTheme.typography.labelLarge.copy(textAlign = TextAlign.Center, color = gray),
                onValueChange = {
                    minuteText = it.toMinute()
                    onTimeSelected(LocalTime.of(hourText.toHour().toInt(), minuteText.toMinute().toInt()))
                    if (minuteText.length == 2)
                        cScope.launch { focusManager.moveFocus(lastFocusDirection) }
                },
                hint = minuteTextHint,
                textStyle = MaterialTheme.typography.titleLarge.copy(fontSize = 28.sp),
                textAlign = TextAlign.Center,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.surface,
                    unfocusedTextColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.scrim,
                    focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.scrim,
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            )
            CustomSpacer(height = 8.dp)
            LabelMediumText(text = stringResource(id = R.string.minute))
        }

    }

}