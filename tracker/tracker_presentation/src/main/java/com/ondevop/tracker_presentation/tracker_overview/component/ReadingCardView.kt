package com.ondevop.tracker_presentation.tracker_overview.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ondevop.core.R
import com.ondevop.core_ui.ReadTeal
import com.ondevop.core_ui.WorkoutRed

@Composable
fun ReadingCardView(
    onCardClick: () -> Unit,
    onReadClick:() ->Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp),
    ) {
        CustomCardView(
            backgroundImage =  R.drawable.read,
            heading = R.string.read,
            subHeading = R.string.daily_goal_reading,
            buttonText = R.string.log_read,
            cardColor = ReadTeal,
            hasImage = true,
            onButtonClick = {

            },
            onCardClick = {
                onCardClick()
            }
        )
    }
}