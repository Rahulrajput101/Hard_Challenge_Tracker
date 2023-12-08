package com.ondevop.tracker_presentation.tracker_overview.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ondevop.core.R
import com.ondevop.core_ui.LocalSpacing
import com.ondevop.core_ui.WaterBlue
import com.ondevop.tracker_presentation.tracker_overview.TrackerOverViewState
import com.ondevop.tracker_presentation.tracker_overview.TrackerOverviewEvent

@Composable
fun WaterCardView(
    state: TrackerOverViewState,
    onDrinkClick: () -> Boolean,
    onCardClick: () -> Unit,
    hasButton : Boolean,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp),
    ) {
        CustomCardView(
            state = state,
            heading = R.string.drink_water,
            subHeading =R.string.daily_goal_water ,
            buttonText = R.string.drink_1l,
            cardColor = WaterBlue,
            hasImage = true,
            hasButton = hasButton,
            onButtonClick = {
                onDrinkClick()
            },
            onCardClick = {

            })

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp),
            horizontalArrangement = Arrangement.End
        ){
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(WaterBlue)
                    .padding(vertical = 6.dp, horizontal = 18.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.drink_track,
                        state.waterIntake
                    ),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }

        }


    }

}
