package com.ondevop.tracker_presentation.tracker_overview.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ondevop.core.R
import com.ondevop.core_ui.foodGreen
import com.ondevop.core_ui.pictureViolet
import com.ondevop.tracker_presentation.tracker_overview.TrackerOverViewState

@Composable
fun DietCardView(
    state: TrackerOverViewState,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp),
    ) {
        CustomCardView(
            state = state,
            backgroundImage =  R.drawable.diet_svg,
            heading = R.string.healthy_diet,
            subHeading = R.string.daily_goal_healthy_diet,
            buttonText = R.string.healthy_diet,
            cardColor = foodGreen,
            hasImage = true,
            hasButton = false,
            onButtonClick = {
                false
            },
            onCardClick = {
                onCardClick()
            })
    }
}
