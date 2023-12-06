package com.proco.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.proco.app.data.model.Skill
import com.proco.base.BaseScreen
import com.proco.domain.model.user.Country
import com.proco.domain.model.user.Expertise
import com.proco.domain.model.user.Gender
import com.proco.domain.model.user.Job
import com.proco.domain.model.user.UserType
import com.proco.domain.usecase.auth.RegisterParam
import com.proco.extention.animateClickable
import com.proco.extention.baseModifier
import com.proco.register.step.RegisterStep1
import com.proco.register.step.RegisterStep2
import com.proco.register.step.RegisterStep3
import com.proco.theme.grayDarker
import com.proco.ui.R
import com.proco.ui.button.ProcoButton
import com.proco.ui.steps.StepScreen
import kotlinx.collections.immutable.ImmutableList

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreenContent(
        registerDto = RegisterParam(),
        onTyping = { _, _ -> },
        onNextStep = { },
        onUserType = {},
        onGender = {},
        onExpertise = {},
        onCountry = {},
        onJobTitle = {},
        onExperience = {},
        onRegister = {},
        allSkills = null
    )
}

@Composable
fun RegisterScreen(vm: RegisterViewModel = hiltViewModel(), onRegister: () -> Unit) {
    val context = LocalContext.current
    val uiState = vm.uiState.collectAsState().value
    val uiEffect = vm.uiEffect.collectAsStateWithLifecycle(null).value
    var errorMessage by remember { mutableStateOf("") }

    val onTyping = remember<((RegisterTypingType, String) -> Unit)> {
        { type, text ->
            vm.onTriggerEvent(RegisterUiEvent.OnTyping(type, text))
        }
    }
    val onUserType = remember<((UserType) -> Unit)> {
        {
            vm.onTriggerEvent(RegisterUiEvent.OnUserType(it))
        }
    }
    val onGender = remember<((Gender) -> Unit)> {
        {
            vm.onTriggerEvent(RegisterUiEvent.OnGender(it))
        }
    }

    BaseScreen(
        modifier = Modifier.baseModifier(),
        alertMessage = errorMessage
    ) {
        RegisterScreenContent(
            registerDto = uiState.data,
            allSkills = uiState.allSkills,
            currentStep = uiState.currentStep,
            onTyping = onTyping,
            onNextStep = { vm.onTriggerEvent(RegisterUiEvent.OnNextStep(it)) },
            onUserType = onUserType,
            onGender = onGender,
            onExperience = { vm.onTriggerEvent(RegisterUiEvent.OnExperience(it)) },
            onCountry = { vm.onTriggerEvent(RegisterUiEvent.OnCountry(it)) },
            onExpertise = { vm.onTriggerEvent(RegisterUiEvent.OnExpertise(it)) },
            onJobTitle = { vm.onTriggerEvent(RegisterUiEvent.OnJobTitle(it)) },
            onRegister = onRegister
        )
    }

    LaunchedEffect(key1 = uiEffect) {
        errorMessage = when (uiEffect) {
            RegisterUiEffect.EmptyEmail -> context.getString(R.string.empty_email)
            RegisterUiEffect.EmptyFamily -> context.getString(R.string.empty_family)
            RegisterUiEffect.EmptyJobTitle -> context.getString(R.string.empty_job_title)
            RegisterUiEffect.EmptyName -> context.getString(R.string.empty_name)
            RegisterUiEffect.EmptyPassword -> context.getString(R.string.empty_password)
            is RegisterUiEffect.ErrorMessage -> uiEffect.message
            null -> ""
            RegisterUiEffect.SuccessRegister -> context.getString(R.string.success_register)
        }
    }
}


@Composable
private fun RegisterScreenContent(
    registerDto: RegisterParam,
    currentStep: Int = 0,
    onTyping: (RegisterTypingType, String) -> Unit,
    onNextStep: (Int) -> Unit,
    onUserType: (UserType) -> Unit,
    onGender: (Gender) -> Unit,
    onExpertise: (Expertise) -> Unit,
    onCountry: (Country) -> Unit,
    onJobTitle: (Job) -> Unit,
    onExperience: (Int) -> Unit,
    onRegister: () -> Unit,
    allSkills: ImmutableList<Skill>?,
) {

    val userType by remember(registerDto.userType) { mutableStateOf(registerDto.userType) }
    val name by remember(registerDto.name) { mutableStateOf(registerDto.name) }
    val family by remember(registerDto.family) { mutableStateOf(registerDto.family) }
    val gender by remember(registerDto.gender) { mutableStateOf(registerDto.gender) }
    val email by remember(registerDto.email) { mutableStateOf(registerDto.email) }
    val password by remember(registerDto.password) { mutableStateOf(registerDto.password) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        StepScreen(
            modifier = Modifier.fillMaxWidth(),
            selectedIndex = currentStep,
            onClick = onNextStep
        )

        Spacer(
            modifier = Modifier
                .padding(vertical = 32.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(grayDarker)
        )

        when (currentStep) {
            0 -> {
                RegisterStep1(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 0.dp, horizontal = 16.dp)
                        .weight(1f),
                    userType = userType,
                    name = name,
                    family = family,
                    gender = gender,
                    email = email,
                    password = password,
                    onTyping = onTyping,
                    onUserType = onUserType,
                    onGender = onGender,
                )
            }

            1 -> {
                RegisterStep2(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 0.dp, horizontal = 16.dp)
                        .weight(1f),
                    job = registerDto.job,
                    expertise = registerDto.expertise,
                    country = registerDto.country,
                    company = registerDto.company,
                    onTyping = onTyping,
                    onExpertise = onExpertise,
                    onCountry = onCountry,
                    onJobTitle = onJobTitle
                )
            }

            2 -> {
                RegisterStep3(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 0.dp, horizontal = 16.dp)
                        .weight(1f),
                    bio = registerDto.bio,
                    selectedSkills = registerDto.skills,
                    allSkills = allSkills,
                    onTyping = onTyping,
                    onExperience = onExperience,
                )
            }
        }

        ProcoButton(
            modifier = Modifier
                .fillMaxWidth()
                .animateClickable(onRegister),
            text = stringResource(id = R.string.next_step),
            onClick = { onNextStep(currentStep + 1) }
        )
    }
}


