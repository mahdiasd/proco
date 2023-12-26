package com.proco.register.step

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proco.domain.fake_data.FakeData
import com.proco.domain.model.user.Education
import com.proco.domain.model.user.Experience
import com.proco.domain.model.user.Skill
import com.proco.extention.animateClickable
import com.proco.extention.blackOrWhite
import com.proco.extention.capitalizeFirstChar
import com.proco.extention.coloredShadow
import com.proco.extention.withColor
import com.proco.register.RegisterTypingType
import com.proco.ui.R
import com.proco.ui.bottom_dialog.ListBottomDialog
import com.proco.ui.dialog_item.EducationItem
import com.proco.ui.dialog_item.ExperienceItem
import com.proco.ui.text.BodyLargeText
import com.proco.ui.text.BodyMediumText
import com.proco.ui.text.TitleLargeText
import com.proco.ui.text_field.ProcoTextField
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Preview(showBackground = true)
@Composable
private fun Preview() {
    RegisterStep3(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        onTyping = { type, text -> },
        experience = null,
        onExperience = {},
        onAddSkill = {},
        onRemoveSkill = {},
        onEducation = {},
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegisterStep3(
    modifier: Modifier,
    bio: String = "",
    selectedSkills: ImmutableList<Skill>? = null,
    allSkills: ImmutableList<Skill>? = FakeData.skills().toImmutableList(),
    onTyping: (RegisterTypingType, String) -> Unit,
    experience: Experience?,
    onExperience: (Experience) -> Unit,
    education: Education? = null,
    onEducation: (Education) -> Unit,
    onAddSkill: (Skill) -> Unit,
    onRemoveSkill: (Skill) -> Unit,
) {
    var skill by remember { mutableStateOf("") }
    var isShowExperienceDialog by remember { mutableStateOf(false) }
    var isShowEducationDialog by remember { mutableStateOf(false) }
    val experienceList = remember { mutableStateListOf(Experience.Junior, Experience.Mid, Experience.Senior, Experience.Expert) }
    val educationList = remember { mutableStateListOf(
        Education.Diploma,
        Education.Associate,
        Education.Bachelors,
        Education.Masters,
        Education.Phd
    ) }

    val suggestSkills by remember(skill) {
        derivedStateOf {
            if (skill.isEmpty()) null
            else allSkills?.filter { it.name.startsWith(skill) }?.toImmutableList()
        }
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, alignment = Alignment.Top)
    ) {

        TitleLargeText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.your_experience),
            textAlign = TextAlign.Start
        )


        if (!suggestSkills.isNullOrEmpty()) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .coloredShadow(MaterialTheme.colorScheme.scrim, 0.5f)
                    .shadow(2.dp, MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.secondary, MaterialTheme.shapes.small)
                    .padding(16.dp),
            ) {
                suggestSkills!!.forEach {
                    BodyMediumText(
                        modifier = Modifier
                            .padding(4.dp)
                            .border(width = 1.dp, color = blackOrWhite(), shape = MaterialTheme.shapes.medium)
                            .padding(8.dp),
                        text = "#${it.name}",
                        textStyle = MaterialTheme.typography.bodyMedium.withColor(blackOrWhite())
                    )
                }
            }
        }

        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            onClick = { isShowExperienceDialog = true },
            value = experience?.title?.let { "$it ${stringResource(id = R.string.year)}" } ?: "",
            onValueChange = { },
            hint = stringResource(R.string.experience),
            enabled = false,
            icon = R.drawable.ic_arrow
        )

        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            onClick = { isShowEducationDialog = true },
            value = education?.toString()?.capitalizeFirstChar() ?: "",
            onValueChange = { },
            hint = stringResource(R.string.experience),
            enabled = false,
            icon = R.drawable.ic_arrow
        )


        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            value = skill,
            onValueChange = { skill = it },
            hint = stringResource(R.string.skill_typing_guide),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            actionNext = false,
            keyboardActions = KeyboardActions(onDone = {
                onAddSkill(Skill(skill))
                skill = ""
            })
        )

        if (!selectedSkills.isNullOrEmpty()) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalArrangement = Arrangement.Center
            ) {
                selectedSkills.forEach {
                    SkillItem(it, onRemoveSkill = onRemoveSkill)
                }
            }
        }

        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            value = bio,
            onValueChange = { onTyping(RegisterTypingType.Bio, it) },
            hint = stringResource(R.string.bio),
            minLines = 4,
            maxLines = 10
        )


    }

    if (isShowExperienceDialog) {
        ListBottomDialog(
            modifier = Modifier.fillMaxWidth(),
            listSize = experienceList.size,
            isShowSearch = false,
            onDismissDialog = { isShowExperienceDialog = false },
            listItem = { index ->
                ExperienceItem(experienceList[index], onClick = {
                    isShowExperienceDialog = false
                    onExperience(experienceList[index])
                })
            }
        )
    }
    if (isShowEducationDialog) {
        ListBottomDialog(
            modifier = Modifier.fillMaxWidth(),
            listSize = educationList.size,
            isShowSearch = false,
            onDismissDialog = { isShowEducationDialog = false },
            listItem = { index ->
                EducationItem(educationList[index], onClick = {
                    isShowEducationDialog = false
                    onEducation(educationList[index])
                })
            }
        )
    }

}

@Composable
fun SkillItem(skill: Skill, onRemoveSkill: (Skill) -> Unit) {
    Row(
        modifier = Modifier
            .padding(end = 8.dp, bottom = 8.dp)
            .background(MaterialTheme.colorScheme.secondary, MaterialTheme.shapes.small)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(20.dp)
                .border(1.dp, MaterialTheme.colorScheme.surface, CircleShape)
                .padding(2.dp)
                .animateClickable { onRemoveSkill(skill) },
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = "Remove Tag",
            tint = MaterialTheme.colorScheme.surface,
        )

        BodyLargeText(
            modifier = Modifier.padding(bottom = 4.dp),
            text = skill.name, color = MaterialTheme.colorScheme.surface, textAlign = TextAlign.Center
        )
    }

}
