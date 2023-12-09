package com.proco.ui.bottom_dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proco.theme.gray
import com.proco.ui.R
import com.proco.ui.text_field.ProcoTextField
import com.proco.ui.vector.EmptyScreen
import com.proco.ui.vector.FailedScreen


@Preview(showBackground = true)
@Composable
private fun Preview() {
    ListBottomDialog(
        listSize = 3,
        onDismissDialog = { },
        listItem = { }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListBottomDialog(
    modifier: Modifier = Modifier,
    lazyColumnModifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp),
    key: ((Int) -> Any)? = null,
    listSize: Int?,
    isLoading: Boolean = false,
    isFailed: Boolean = false,
    isShowSearch: Boolean = true,
    onDismissDialog: () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    listItem: @Composable (index: Int) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    ModalBottomSheet(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        onDismissRequest = onDismissDialog
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            if (isShowSearch) {
                ProcoTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = searchText,
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = containerColor,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = gray,
                        focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        unfocusedContainerColor = containerColor,
                    ),
                    onValueChange = {
                        searchText = it
                    },
                    hint = stringResource(id = R.string.search)
                )
            }

            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.size(25.dp), color = MaterialTheme.colorScheme.primary)
                }

                isFailed -> {
                    FailedScreen(onRetry = {})
                }

                listSize == null || listSize == 0 -> {
                    EmptyScreen(Modifier.fillMaxWidth())
                }

                else -> {
                    LazyColumn(
                        modifier = lazyColumnModifier,
                        content = {
                            items(listSize ?: 0, key = key) { index ->
                                listItem(index)
                            }
                        })
                }
            }
        }
    }

}




