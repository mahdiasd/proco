package com.proco.register.step

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proco.domain.model.user.Gender
import com.proco.domain.model.user.UserType
import com.proco.extention.animateClickable
import com.proco.extention.uiName
import com.proco.register.RegisterTypingType
import com.proco.theme.white
import com.proco.ui.bottom_dialog.ListBottomDialog
import com.proco.ui.dialog_item.GenderItems
import com.proco.ui.text.TitleMediumText
import com.proco.ui.text_field.ProcoTextField

@Preview(showBackground = true)
@Composable
private fun Preview() {
    RegisterStep1(
        modifier = Modifier.fillMaxSize(),
        onTyping = { type, text -> },
        onUserType = {},
        onGender = {}
    )
}

@Composable
fun RegisterStep1(
    modifier: Modifier,
    userType: UserType = UserType.Mentee,
    name: String = "",
    family: String = "",
    gender: Gender? = null,
    email: String = "",
    password: String = "",
    onTyping: (RegisterTypingType, String) -> Unit,
    onUserType: (UserType) -> Unit,
    onGender: (Gender) -> Unit
) {
    val context = LocalContext.current
    var isShowGenderDialog by remember { mutableStateOf(false) }

    val genderOptions = remember { mutableStateListOf(Gender.Male, Gender.Female, Gender.NonBinary) }

    Column(
        modifier = modifier
            .imePadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp, alignment = Alignment.Top)
    ) {

        Row(
            modifier = Modifier
                .shadow(4.dp, MaterialTheme.shapes.extraLarge, spotColor = MaterialTheme.colorScheme.secondary)
                .background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.extraLarge),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            TitleMediumText(
                modifier = Modifier
                    .animateClickable { onUserType(UserType.Mentee) }
                    .then(
                        when (userType) {
                            is UserType.Mentee -> Modifier.background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.extraLarge)
                            is UserType.Mentor -> Modifier.background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.extraLarge)
                        }
                    )
                    .padding(vertical = 16.dp, horizontal = 24.dp),
                text = stringResource(id = com.proco.ui.R.string.mentee),
                color = if (userType is UserType.Mentee) white else MaterialTheme.colorScheme.surface
            )

            TitleMediumText(
                modifier = Modifier
                    .animateClickable { onUserType(UserType.Mentor) }
                    .then(
                        when (userType) {
                            is UserType.Mentor -> Modifier.background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.extraLarge)
                            is UserType.Mentee -> Modifier.background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.extraLarge)
                        }
                    )
                    .padding(vertical = 16.dp, horizontal = 24.dp),
                text = stringResource(id = com.proco.ui.R.string.mentor),
                color = if (userType is UserType.Mentor) white else MaterialTheme.colorScheme.surface
            )
        }


        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = { onTyping(RegisterTypingType.Name, it) },
            hint = stringResource(com.proco.ui.R.string.name)
        )

        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            value = family,
            onValueChange = { onTyping(RegisterTypingType.Family, it) },
            hint = stringResource(com.proco.ui.R.string.family)
        )

        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            value = gender?.uiName()?.let { stringResource(id = it) } ?: "",
            onValueChange = { },
            hint = stringResource(com.proco.ui.R.string.gender),
            enabled = false,
            icon = com.proco.ui.R.drawable.ic_arrow,
            onClick = { isShowGenderDialog = true },
        )

        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { onTyping(RegisterTypingType.Email, it) },
            hint = stringResource(com.proco.ui.R.string.email)
        )

        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { onTyping(RegisterTypingType.Password, it) },
            hint = stringResource(com.proco.ui.R.string.password),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )
    }

    if (isShowGenderDialog) {
        ListBottomDialog(
            modifier = Modifier.fillMaxWidth(),
            listSize = 3,
            isShowSearch = false,
            onDismissDialog = { isShowGenderDialog = false },
            listItem = { index ->
                GenderItems(genderOptions[index], onClick = {
                    isShowGenderDialog = false
                    onGender(genderOptions[index])
                })
            }
        )
    }
}

