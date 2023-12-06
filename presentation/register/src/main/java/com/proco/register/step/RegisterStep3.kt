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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proco.app.data.model.Skill
import com.proco.domain.fake_data.FakeData
import com.proco.extention.blackOrWhite
import com.proco.extention.coloredShadow
import com.proco.extention.withColor
import com.proco.register.RegisterTypingType
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
        onExperience = {}
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
    onExperience: (Int) -> Unit,
) {
    var skill by remember { mutableStateOf("") }

    val suggestSkills by remember(skill) {
        derivedStateOf {
            if (skill.isEmpty()) null
            else allSkills?.filter { it.name.startsWith(skill) }?.toImmutableList()
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, alignment = Alignment.Top)
    ) {

        TitleLargeText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(com.proco.ui.R.string.your_experience),
            textAlign = TextAlign.Start
        )

//        StepSeekBar(
//            texts = listOf("Mid-Level", "Senior", "Expert").toImmutableList(),
//            onStep = { onExperience(it) }
//        )

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
                        text = "#${it.name}", textStyle = MaterialTheme.typography.bodyMedium.withColor(blackOrWhite())
                    )
                }
            }
        }


        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            value = skill,
            onValueChange = { skill = it },
            hint = stringResource(com.proco.ui.R.string.skills)
        )

        if (!selectedSkills.isNullOrEmpty()) {
            FlowRow(
                modifier = Modifier.padding(8.dp),
            ) {
                selectedSkills.forEach {
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
                            .padding(4.dp)
                    ) {
                        Icon(painter = painterResource(id = com.proco.ui.R.drawable.ic_close), contentDescription = "Close Tag")
                        BodyMediumText(text = it.name, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }

        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            value = bio,
            onValueChange = { onTyping(RegisterTypingType.Bio, it) },
            hint = stringResource(com.proco.ui.R.string.bio),
            minLines = 4,
            maxLine = 10
        )

    }
}
