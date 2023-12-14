package com.ondevop.tracker_presentation.tracker_overview.component

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.ondevop.core_ui.LocalSpacing
import com.ondevop.core_ui.composables.CircularImage
import com.ondevop.tracker_presentation.R
import com.ondevop.tracker_presentation.component.UnitDisplay
import com.ondevop.tracker_presentation.tracker_overview.TrackerOverViewState

@Composable
fun TrackerHeader(
    state: TrackerOverViewState,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current

    val animateDaysCount = animateIntAsState(
        targetValue = state.totalDays, label = ""
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
                start = spacing.spaceMedium,
                end = spacing.spaceMedium
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GreetingSection()
            CircularImage(
                modifier = Modifier,
                imageUri = state.imageUri?.toUri(),
                size = 60.dp
            ) {

            }
        }
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            UnitDisplay(
                amount = animateDaysCount.value,
                unit = stringResource(id = com.ondevop.core.R.string.days),
                amountColor = MaterialTheme.colorScheme.onPrimary,
                unitColor = MaterialTheme.colorScheme.onPrimary,
                amountTextSize = 40.sp,
                modifier = Modifier.align(Alignment.Bottom)
            )

            Column {
                Text(
                    text = stringResource(id = com.ondevop.core.R.string.your_goal),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                UnitDisplay(
                    amount = state.goal,
                    unit = stringResource(id = com.ondevop.core.R.string.days),
                    amountColor = MaterialTheme.colorScheme.onPrimary,
                    unitColor = MaterialTheme.colorScheme.onPrimary,
                    amountTextSize = 40.sp,
                )

            }

        }
        DaysBar(
            days = state.totalDays,
            goal =state.goal,
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        )


    }


}