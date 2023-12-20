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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proco.base.BaseScreen
import com.proco.domain.model.user.Country
import com.proco.domain.model.user.Education
import com.proco.domain.model.user.Experience
import com.proco.domain.model.user.Expertise
import com.proco.domain.model.user.Gender
import com.proco.domain.model.user.Job
import com.proco.domain.model.user.Skill
import com.proco.domain.model.user.UserType
import com.proco.domain.usecase.auth.RegisterParam
import com.proco.extention.animateClickable
import com.proco.extention.baseModifier
import com.proco.extention.dLog
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
        registerParam = RegisterParam(),
        onTyping = { _, _ -> },
        onNextStep = { },
        onUserType = {},
        onGender = {},
        onExpertise = {},
        onCountry = {},
        onJobTitle = {},
        onExperience = {},
        onRegister = {},
        allCountries = null,
        allJobs = null,
        allSkills = null,
        onAddSkill = {},
        onRemoveSkill = {},
        onRemoveExpertise = {},
        onEducation = {}
    )
}

@Composable
fun RegisterScreen(vm: RegisterViewModel = hiltViewModel(), onRegister: () -> Unit) {
    val uiState = vm.uiState.collectAsState().value
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
        uiMessage = uiState.uiMessage
    ) {
        RegisterScreenContent(
            registerParam = uiState.data,
            allSkills = uiState.allSkills,
            currentStep = uiState.currentStep,
            onTyping = onTyping,
            onNextStep = { vm.onTriggerEvent(RegisterUiEvent.OnNextStep(it)) },
            onUserType = onUserType,
            isLoading = uiState.isLoading,
            onGender = onGender,
            allJobs = uiState.allJobs,
            allCountries = uiState.allCountries,
            onExperience = { vm.onTriggerEvent(RegisterUiEvent.OnExperience(it)) },
            onCountry = { vm.onTriggerEvent(RegisterUiEvent.OnCountry(it)) },
            onExpertise = { vm.onTriggerEvent(RegisterUiEvent.OnExpertise(it)) },
            onRemoveExpertise = { vm.onTriggerEvent(RegisterUiEvent.OnRemoveExpertise(it)) },
            onJobTitle = { vm.onTriggerEvent(RegisterUiEvent.OnJobTitle(it)) },
            onRegister = onRegister,
            onAddSkill = { vm.onTriggerEvent(RegisterUiEvent.OnAddSkill(it)) },
            onRemoveSkill = { vm.onTriggerEvent(RegisterUiEvent.OnRemoveSkill(it)) },
            onEducation = { vm.onTriggerEvent(RegisterUiEvent.OnEducation(it)) }
        )
    }
}


@Composable
private fun RegisterScreenContent(
    registerParam: RegisterParam,
    currentStep: Int = 0,
    onTyping: (RegisterTypingType, String) -> Unit,
    onNextStep: (Int) -> Unit,
    onUserType: (UserType) -> Unit,
    onGender: (Gender) -> Unit,
    onExpertise: (Expertise) -> Unit,
    onRemoveExpertise: (Expertise) -> Unit,
    onCountry: (Country) -> Unit,
    onJobTitle: (Job) -> Unit,
    onExperience: (Experience) -> Unit,
    onRegister: () -> Unit,
    isLoading: Boolean = false,
    allSkills: ImmutableList<Skill>?,
    allJobs: ImmutableList<Job>?,
    allCountries: ImmutableList<Country>?,
    onAddSkill: (Skill) -> Unit,
    onRemoveSkill: (Skill) -> Unit,
    onEducation: (Education) -> Unit,

    ) {

    val userType by remember(registerParam.userType) { mutableStateOf(registerParam.userType) }
    val name by remember(registerParam.name) { mutableStateOf(registerParam.name) }
    val family by remember(registerParam.family) { mutableStateOf(registerParam.family) }
    val gender by remember(registerParam.gender) { mutableStateOf(registerParam.gender) }
    val email by remember(registerParam.email) { mutableStateOf(registerParam.email) }
    val password by remember(registerParam.password) { mutableStateOf(registerParam.password) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        StepScreen(
            modifier = Modifier.fillMaxWidth(),
            selectedIndex = currentStep,
            onClick = {
                it.dLog("o")
                onNextStep(it)
            }
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
                    allCountries = allCountries,
                    allJobs = allJobs,
                    job = registerParam.job,
                    expertises = registerParam.expertises,
                    country = registerParam.country,
                    company = registerParam.company,
                    onTyping = onTyping,
                    onExpertise = onExpertise,
                    onCountry = onCountry,
                    onJobTitle = onJobTitle,
                    onRemoveExpertise = onRemoveExpertise
                )
            }

            2 -> {
                RegisterStep3(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 0.dp, horizontal = 16.dp)
                        .weight(1f),
                    bio = registerParam.bio,
                    selectedSkills = registerParam.skills,
                    experience = registerParam.experience,
                    allSkills = allSkills,
                    onTyping = onTyping,
                    onExperience = onExperience,
                    onAddSkill = onAddSkill,
                    onRemoveSkill = onRemoveSkill,
                    education = registerParam.education,
                    onEducation = onEducation
                )
            }
        }

        ProcoButton(
            modifier = Modifier
                .fillMaxWidth()
                .animateClickable(onRegister),
            isLoading = isLoading,
            text = stringResource(id = R.string.next_step),
            onClick = { onNextStep(currentStep + 1) }
        )
    }
}


