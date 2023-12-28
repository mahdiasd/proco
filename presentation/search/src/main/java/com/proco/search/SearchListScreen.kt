package com.proco.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proco.base.BaseScreen
import com.proco.domain.fake_data.FakeData
import com.proco.domain.model.filter.UserFilter
import com.proco.domain.model.user.UserSummary
import com.proco.extention.animateClickable
import com.proco.extention.baseModifier
import com.proco.extention.blackOrWhite
import com.proco.extention.coloredShadow
import com.proco.theme.ProcoTheme
import com.proco.theme.white
import com.proco.ui.R
import com.proco.ui.list.SwipeList
import com.proco.ui.text.LabelMediumText
import com.proco.ui.text_field.ProcoTextField
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Preview(showBackground = true)
@Composable
private fun Preview(darkTheme: Boolean = false) {
    ProcoTheme(darkTheme) {
        SearchScreenContent(
            users = FakeData.usersSummary().toImmutableList(),
            onFilter = { },
            onItemClick = { },
            onSearchChanged = {},
            onLoadMore = {},
            onRefresh = {},
            userFilter = null,
            searchText = ""
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DarkPreview() {
    Preview(true)
}

@Composable
fun SearchScreen(
    vm: SearchViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit,
    onFilter: () -> Unit,
) {
    val uiState = vm.uiState.collectAsState().value

    BaseScreen(
        modifier = Modifier.baseModifier(),
        uiMessage = uiState.uiMessage
    ) {
        SearchScreenContent(
            users = uiState.data,
            searchText = uiState.searchText,
            isLoadMore = uiState.isLoadMore,
            isRefreshing = uiState.isLoading,
            userFilter = uiState.userFilter,
            onSearchChanged = { vm.onTriggerEvent(SearchViewEvent.OnTyping(it)) },
            onRefresh = { vm.onTriggerEvent(SearchViewEvent.OnRefresh) },
            onLoadMore = { vm.onTriggerEvent(SearchViewEvent.OnNextPage) },
            onItemClick = onItemClick,
            onFilter = onFilter,
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenContent(
    users: ImmutableList<UserSummary>?,
    userFilter: UserFilter?,
    searchText: String,
    isRefreshing: Boolean = false,
    isLoadMore: Boolean = false,
    onSearchChanged: (String) -> Unit = {},
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    onFilter: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    val filterCount by remember(userFilter) { derivedStateOf { userFilter?.getCount() } }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProcoTextField(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .background(blackOrWhite(), MaterialTheme.shapes.medium)
                    .coloredShadow(MaterialTheme.colorScheme.tertiary, alpha = 0.5f)
                    .shadow(1.dp, MaterialTheme.shapes.medium),
                value = searchText,
                onValueChange = onSearchChanged,
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = blackOrWhite(),
                    focusedContainerColor = blackOrWhite(),
                    unfocusedContainerColor = blackOrWhite(),
                ),
                hint = stringResource(id = R.string.search)
            )

            BadgedBox(
                badge = {
                    Badge(modifier = Modifier.padding(end = 16.dp), containerColor = MaterialTheme.colorScheme.primary) {
                        LabelMediumText(
                            modifier = Modifier,
                            text = "$filterCount",
                            textAlign = TextAlign.Center,
                            color = white
                        )
                    }
                },

                content = {
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .animateClickable(onFilter),
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = "filter",
                        tint = MaterialTheme.colorScheme.scrim
                    )
                }
            )
        }

        SwipeList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            isRefreshing = isRefreshing,
            isLoadMore = isLoadMore,
            listSize = users?.size,
            onRefresh = onRefresh,
            onLoadMore = onLoadMore,
        ) { index ->
            if (users != null)
                MentorItem(users[index], onClick = {
                    onItemClick(users[index].id)
                })
        }
    }
}



