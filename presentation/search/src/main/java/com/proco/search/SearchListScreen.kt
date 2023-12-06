package com.proco.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proco.base.BaseScreen
import com.proco.domain.fake_data.FakeData
import com.proco.domain.model.user.User
import com.proco.extention.animateClickable
import com.proco.extention.blackOrWhite
import com.proco.extention.coloredShadow
import com.proco.theme.ProcoTheme
import com.proco.ui.R
import com.proco.ui.list.SwipeList
import com.proco.ui.text_field.ProcoTextField
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ProcoTheme {
        SearchScreenContent(
            onFilter = { },
            onItemClick = { }
        )
    }
}

@Composable
fun SearchScreen(
    vm: SearchViewModel = hiltViewModel(),
    onItemClick: () -> Unit,
    onFilter: () -> Unit,
) {
    val uiState = vm.uiState.collectAsState().value

    BaseScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        alertMessage = uiState.alertMessage
    ) {
        SearchScreenContent(
            onFilter = onFilter,
            onItemClick = onItemClick
        )
    }

}


@Composable
private fun SearchScreenContent(
    users: ImmutableList<User> = FakeData.users().toImmutableList(),
    searchText: String = "",
    onSearchChanged: (String) -> Unit = {},
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    isRefreshing: Boolean = false,
    isLoadMore: Boolean = false,
    onFilter: () -> Unit,
    onItemClick: () -> Unit
) {
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

                .statusBarsPadding()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProcoTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = 16.dp)
                    .coloredShadow(MaterialTheme.colorScheme.tertiary, alpha = 0.5f)
                    .shadow(1.dp, MaterialTheme.shapes.medium)
                    .background(blackOrWhite(), MaterialTheme.shapes.medium),
                value = searchText,
                onValueChange = onSearchChanged,
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

            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .animateClickable(onFilter),
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "filter",
                tint = MaterialTheme.colorScheme.scrim
            )
        }

        SwipeList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            isRefreshing = isRefreshing,
            isLoadMore = isLoadMore,
            listSize = users.size,
            onRefresh = onRefresh,
            onLoadMore = onLoadMore,
        ) { index ->
            MentorItem(users[index], onClick = onItemClick)
        }
    }
}



