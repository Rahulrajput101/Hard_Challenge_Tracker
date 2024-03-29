package com.ondevop.tracker_presentation.tracker_overview.component

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ondevop.core_ui.LocalSpacing
import com.ondevop.core_domain.R
import com.ondevop.tracker_presentation.component.UnitDisplay
import com.ondevop.tracker_presentation.tracker_overview.TrackerOverViewState

@Composable
fun TrackerHeader(
    modifier: Modifier = Modifier,
    state: TrackerOverViewState,
    challengeGoal: Int,
    totalDays: Int,
    onMenuItemClick: () -> Unit,
    onPremiumClick: () -> Unit
) {
    val spacing = LocalSpacing.current

    val animateDaysCount = animateIntAsState(
        targetValue = totalDays, label = ""
    )


    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = 15.dp,
                    bottomEnd = 15.dp
                )
            )
            .background(MaterialTheme.colorScheme.primary)
            .padding(
                top = spacing.spaceSmall,
                bottom = spacing.spaceLarge,
                end = spacing.spaceMedium
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // GreetingSection()
            IconButton(
                onClick = {
                    onMenuItemClick()
                },
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.menu_icon),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(
                text = stringResource(id = R.string.tracker),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = FontFamily(
                    Font(
                        R.font.rubik_medium,
                        FontWeight.Bold
                    )
                )
            )

            Image(
                painter = painterResource(id = R.drawable.crown_svgrepo_com),
                alignment = Alignment.CenterEnd,
                contentDescription = "premium icon",
                modifier = Modifier.size(30.dp)
                    .clickable { onPremiumClick() }
            )

        }
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = spacing.spaceMedium
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            UnitDisplay(
                amount = animateDaysCount.value,
                unit = stringResource(id = R.string.days),
                amountColor = MaterialTheme.colorScheme.onPrimary,
                unitColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(Alignment.Bottom)
            )

            Column {
                Text(
                    text = stringResource(id = R.string.your_goal),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                UnitDisplay(
                    amount = challengeGoal,
                    unit = stringResource(id = R.string.days),
                    amountColor = MaterialTheme.colorScheme.onPrimary,
                    unitColor = MaterialTheme.colorScheme.onPrimary,
                )

            }

        }
        DaysBar(
            days = totalDays,
            goal = challengeGoal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = spacing.spaceMedium
                )
                .height(26.dp)
        )
    }


}