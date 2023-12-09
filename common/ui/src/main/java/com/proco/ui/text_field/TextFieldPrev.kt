package com.proco.ui.text_field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proco.extention.animateClickable
import com.proco.extention.withColor
import com.proco.theme.ProcoTheme
import com.proco.theme.gray
import com.proco.theme.white
import com.proco.ui.R
import com.proco.ui.text.BodyMediumText

@Preview
@Composable
fun TextFieldPrev() {
    ProcoTheme {
        ProcoTextField(value = "Name", onValueChange = {}, hint = "")
    }
}

@Preview
@Composable
fun TextFieldIconPrev() {
    ProcoTheme {
        ProcoTextField(value = "Name", onValueChange = {}, hint = "", icon = R.drawable.ic_arrow)
    }
}

@Composable
fun ProcoTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    maxLines: Int = 1,
    minLines: Int = 1,
    shape: CornerBasedShape = MaterialTheme.shapes.small,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.surface,
        unfocusedTextColor = MaterialTheme.colorScheme.surface,
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
        focusedContainerColor = MaterialTheme.colorScheme.secondary,
        unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
    ),
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
    hintTextStyle: TextStyle = MaterialTheme.typography.bodyMedium.withColor(gray),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    readOnly: Boolean = false,
    textAlign: TextAlign = TextAlign.Start,
    actionNext: Boolean = maxLines == 1
) {


    OutlinedTextField(
        modifier = modifier,
        value = value,
        shape = shape,
        onValueChange = onValueChange,
        maxLines = maxLines,
        textStyle = textStyle.copy(textAlign = textAlign),
        readOnly = readOnly,
        minLines = minLines,
        colors = colors,
        keyboardActions = keyboardActions,
        keyboardOptions = if (actionNext) keyboardOptions.copy(imeAction = ImeAction.Next) else keyboardOptions,
        placeholder = {
            BodyMediumText(
                text = hint,
                modifier = Modifier.fillMaxWidth(),
                textStyle = hintTextStyle,
                color = if (value.isEmpty()) gray else white
            )
        },
    )


}


@Composable
fun ProcoTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    maxLine: Int = 1,
    shape: CornerBasedShape = MaterialTheme.shapes.small,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.surface,
        unfocusedTextColor = MaterialTheme.colorScheme.surface,
        disabledTextColor = MaterialTheme.colorScheme.surface,
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
        disabledBorderColor = MaterialTheme.colorScheme.secondary,

        focusedContainerColor = MaterialTheme.colorScheme.secondary,
        unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
        disabledContainerColor = MaterialTheme.colorScheme.secondary,

        ),
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
    ),
    hintTextStyle: TextStyle = MaterialTheme.typography.bodyMedium.withColor(gray),
    readOnly: Boolean = false,
    enabled: Boolean = readOnly,
    icon: Int,
    iconModifier: Modifier = Modifier.size(16.dp),
    iconTint: Color = gray,
    onClick: (() -> Unit)? = null
) {

    OutlinedTextField(
        modifier = modifier
            .then(if (onClick != null) Modifier.animateClickable(onClick) else Modifier),
        value = value,
        shape = shape,
        onValueChange = onValueChange,
        maxLines = maxLine,
        textStyle = textStyle,
        readOnly = readOnly,
        enabled = enabled,
        colors = colors,
        keyboardOptions = keyboardOptions,
        trailingIcon = {
            Icon(
                modifier = iconModifier,
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = iconTint
            )
        },
        placeholder = {
            BodyMediumText(
                text = hint,
                modifier = Modifier.fillMaxWidth(),
                textStyle = hintTextStyle,
                color = if (value.isEmpty()) gray else white
            )
        },
    )


}



