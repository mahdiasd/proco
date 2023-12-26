package com.proco.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.proco.domain.fake_data.FakeData
import com.proco.domain.model.user.UserSummary
import com.proco.extention.animateClickable
import com.proco.extention.blackOrWhite
import com.proco.extention.coilCircle
import com.proco.extention.coloredShadow
import com.proco.extention.withColor
import com.proco.theme.ProcoTheme
import com.proco.ui.R
import com.proco.ui.text.BodyLargeText
import com.proco.ui.text.BodyMediumText
import com.proco.utils.ProcoGravity

@Preview
@Composable
private fun Preview() {
    ProcoTheme {
        MentorItem(user = FakeData.usersSummary().first(), onClick = {})
    }
}

@Composable
fun MentorItem(user: UserSummary, onClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .animateClickable(onClick)
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        val paddingBox = 12.dp
        val avatarRef = createRef()
        val spacerRef = createRef()
        val nameRef = createRef()
        val expertiseRef = createRef()
        val endBoxRef = createRef()

        Spacer(
            modifier = Modifier
                .coloredShadow(MaterialTheme.colorScheme.tertiary, alpha = 0.5f)
                .shadow(3.dp, MaterialTheme.shapes.large)
                .background(blackOrWhite(), MaterialTheme.shapes.large)
                .constrainAs(spacerRef)
                {
                    top.linkTo(avatarRef.top, margin = 24.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                    end.linkTo(parent.end, 24.dp)
                    bottom.linkTo(endBoxRef.bottom)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }

        )

        AsyncImage(
            modifier = Modifier
                .size(64.dp)
                .constrainAs(avatarRef) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            model = coilCircle(data = user.avatar.ifEmpty { R.drawable.ic_placeholer_avatar }),
            contentDescription = "Avatar",
        )

        Column(Modifier.constrainAs(nameRef)
        {
            top.linkTo(spacerRef.top, margin = paddingBox)
            end.linkTo(spacerRef.end, paddingBox)
            start.linkTo(spacerRef.start, paddingBox)
            width = Dimension.fillToConstraints
        }) {
            BodyLargeText(
                modifier = Modifier,
                text = "${user.name} ${user.family}",
                textStyle = MaterialTheme.typography.bodyLarge.withColor(MaterialTheme.colorScheme.primary)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BodyMediumText(
                    text = "${user.gender}"
                )

                BodyMediumText(
                    modifier = Modifier.widthIn(30.dp),
                    text = "Iran",
                    icon = R.drawable.ic_location,
                    iconGravity = ProcoGravity.Left
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BodyMediumText(
                    text = "#kotlin #Java #Jetpack-compose"
                )

                BodyMediumText(
                    modifier = Modifier.widthIn(30.dp),
                    text = "5",
                    icon = R.drawable.ic_dollar,
                    iconGravity = ProcoGravity.Left
                )
            }
        }

        Spacer(modifier = Modifier
            .height(paddingBox)
            .constrainAs(endBoxRef) {
                top.linkTo(nameRef.bottom)
                start.linkTo(parent.start)
            })

    }
}
