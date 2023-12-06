package com.proco.register.step

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proco.domain.fake_data.FakeData
import com.proco.domain.model.user.Country
import com.proco.domain.model.user.Expertise
import com.proco.domain.model.user.Job
import com.proco.register.RegisterTypingType
import com.proco.theme.ProcoTheme
import com.proco.ui.bottom_dialog.ListBottomDialog
import com.proco.ui.dialog_item.CountryItem
import com.proco.ui.dialog_item.ExpertiseItem
import com.proco.ui.dialog_item.JobItems
import com.proco.ui.text.TitleLargeText
import com.proco.ui.text_field.ProcoTextField
import kotlinx.collections.immutable.ImmutableList


@Preview(showBackground = true)
@Composable
private fun Preview() {
    ProcoTheme {
        RegisterStep2(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            job = Job(name = "", expertises = FakeData.expertises()),
            expertise = Expertise(name = ""),
            country = Country(name = "", code = ""),
            onTyping = { type, text -> },
            onJobTitle = {},
            onCountry = {},
            onExpertise = {}
        )
    }
}

@Composable
fun RegisterStep2(
    modifier: Modifier,
    countries: ImmutableList<Country> = FakeData.countries(),
    jobs: ImmutableList<Job> = FakeData.jobTitles(),
    job: Job?,
    expertise: Expertise?,
    country: Country?,
    company: String = "",
    onTyping: (RegisterTypingType, String) -> Unit,
    onJobTitle: (Job) -> Unit,
    onCountry: (Country) -> Unit,
    onExpertise: (Expertise) -> Unit,
) {
    var isShowJobTitleDialog by remember { mutableStateOf(false) }
    var isShowExpertiseDialog by remember { mutableStateOf(false) }
    var isShowCountryDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp, alignment = Alignment.Top)
    ) {

        TitleLargeText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(com.proco.ui.R.string.job_information)
        )

        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            onClick = { isShowJobTitleDialog = true },
            value = job?.name ?: "",
            onValueChange = { },
            hint = stringResource(com.proco.ui.R.string.job_title),
            enabled = false,
            icon = com.proco.ui.R.drawable.ic_arrow
        )

        if (job != null) {
            ProcoTextField(
                modifier = Modifier.fillMaxWidth(),
                value = expertise?.name ?: "",
                onClick = { isShowExpertiseDialog = true },
                onValueChange = { },
                hint = stringResource(com.proco.ui.R.string.expertise),
                enabled = false,
                icon = com.proco.ui.R.drawable.ic_arrow
            )
        }

        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            onClick = { isShowCountryDialog = true },
            value = country?.name ?: "",
            onValueChange = { },
            hint = stringResource(com.proco.ui.R.string.country),
            enabled = false,
            icon = com.proco.ui.R.drawable.ic_arrow
        )

        ProcoTextField(
            modifier = Modifier.fillMaxWidth(),
            value = company,
            onValueChange = { onTyping(RegisterTypingType.Company, it) },
            hint = stringResource(com.proco.ui.R.string.company),
        )

    }


    if (isShowJobTitleDialog) {
        ListBottomDialog(
            modifier = Modifier.fillMaxWidth(),
            listSize = 3,
            isShowSearch = false,
            onDismissDialog = { isShowCountryDialog = false },
            listItem = { index ->
                JobItems(jobs[index], onClick = {
                    isShowJobTitleDialog = false
                    onJobTitle(jobs[index])
                })
            }
        )
    }

    if (isShowCountryDialog) {
        ListBottomDialog(
            modifier = Modifier.fillMaxWidth(),
            listSize = 3,
            isShowSearch = false,
            onDismissDialog = { isShowCountryDialog = false },
            listItem = { index ->
                CountryItem(countries[index], onClick = {
                    isShowCountryDialog = false
                    onCountry(countries[index])
                })
            }
        )
    }

    if (isShowExpertiseDialog) {
        ListBottomDialog(
            modifier = Modifier.fillMaxWidth(),
            listSize = 3,
            isShowSearch = false,
            onDismissDialog = { isShowExpertiseDialog = false },
            listItem = { index ->
                ExpertiseItem(job!!.expertises[index], onClick = {
                    isShowExpertiseDialog = false
                    onExpertise(job.expertises[index])
                })
            }
        )
    }

}

