package com.proco.ui.dialog_item

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.proco.extention.animateClickable
import com.proco.theme.white
import com.proco.ui.R
import com.proco.ui.button.ProcoButton
import com.proco.ui.text.BodyLargeText
import com.proco.ui.text.TitleMediumText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceDialog(
    @StringRes title: Int = R.string.choose_price_for_every_minute,
    isLoading: Boolean,
    onPrice: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var price by remember { mutableIntStateOf(1) }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .heightIn(min = 250.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TitleMediumText(text = stringResource(id = title), maxLines = 2)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .padding(16.dp)
                        .animateClickable {
                            if (price > 1) price -= 1
                        },
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = "plus icon",
                    tint = white
                )

                BodyLargeText(
                    modifier = Modifier
                        .size(80.dp, 48.dp)
                        .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                        .padding(8.dp),
                    text = "$price $",
                    textAlign = TextAlign.Center,
                    color = white
                )

                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .padding(16.dp)
                        .animateClickable {
                            if (price < 9) price += 1
                        },
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "plus icon",
                    tint = white
                )

            }

            ProcoButton(isLoading = isLoading, text = stringResource(id = R.string.save), onClick = { onPrice(price) })

        }
    }
}
