package com.ondevop.tracker_presentation.tracker_overview.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ondevop.core_domain.R
import com.ondevop.core_ui.ReadTeal
import com.ondevop.core_ui.WorkoutRed
import com.ondevop.tracker_presentation.tracker_overview.TrackerOverViewState

@Composable
fun ReadingCardView(
    state: TrackerOverViewState,
    onCardClick: () -> Unit,
    onReadClick:() ->Boolean,
    hasButton : Boolean,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp),
    ) {
        CustomCardView(
            state = state,
            backgroundImage =  R.drawable.read,
            heading = R.string.read,
            subHeading = R.string.daily_goal_reading,
            buttonText = R.string.log_read,
            cardColor = ReadTeal,
            hasImage = true,
            hasButton = hasButton,
            onButtonClick = {
                    onReadClick()
            },
            onCardClick = {
                onCardClick()
            }
        )
    }
}