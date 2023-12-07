package com.proco.ui.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.proco.extention.animateClickable
import com.proco.theme.gray
import com.proco.theme.white
import com.proco.ui.text.TitleMediumText

@Preview(showBackground = true)
@Composable
private fun StepPreview() {
    StepScreen(modifier = Modifier.fillMaxWidth(), selectedIndex = 0, onClick = {})
}

@Composable
fun StepScreen(modifier: Modifier = Modifier, selectedIndex: Int, onClick: (Int) -> Unit) {
    val steps = remember { mutableStateListOf("Step 1", "Step 2", "Step 3") }

    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        content = {
            items(steps.size) { index ->
                StepItem(
                    number = index + 1,
                    text = steps[index],
                    isLastItem = index == steps.size - 1,
                    isSelected = index <= selectedIndex,
                    isLineSelected = index == selectedIndex,
                    onClick = { onClick(index) }
                )
            }
        })
}

@Composable
private fun StepItem(number: Int, text: String, isLastItem: Boolean, isSelected: Boolean, isLineSelected: Boolean, onClick: () -> Unit) {
    ConstraintLayout(modifier = Modifier.animateClickable { onClick() }) {
        val numberRef = createRef()
        TitleMediumText(
            modifier = Modifier
                .size(40.dp, 40.dp)
                .constrainAs(numberRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .then(
                    if (isSelected) Modifier.background(MaterialTheme.colorScheme.primary, CircleShape)
                    else Modifier.border(1.dp, gray, CircleShape)
                ),
            text = "$number",
            color = if (isSelected) white else gray,
            forceVerticalCenter = true
        )

        TitleMediumText(
            modifier = Modifier
                .constrainAs(createRef()) {
                    start.linkTo(parent.start)
                    top.linkTo(numberRef.bottom, margin = 8.dp)
                },
            text = text,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        )
        if (!isLastItem) {
            Spacer(
                modifier = Modifier
                    .constrainAs(createRef())
                    {
                        start.linkTo(numberRef.end)
                        top.linkTo(numberRef.top)
                        bottom.linkTo(numberRef.bottom)
                    }
                    .size(100.dp, 1.dp)
                    .then(
                        if (isLineSelected) Modifier.background(MaterialTheme.colorScheme.primary)
                        else Modifier.background(gray)
                    )
            )
        }
    }

}

