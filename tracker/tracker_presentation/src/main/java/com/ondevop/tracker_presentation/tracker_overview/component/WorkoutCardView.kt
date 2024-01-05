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
import com.ondevop.core_domain.R
import com.ondevop.core_ui.WaterBlue
import com.ondevop.core_ui.WorkoutRed
import com.ondevop.tracker_presentation.tracker_overview.TrackerOverViewState

@Composable
fun WorkoutCardView(
    state : TrackerOverViewState,
    onCardClick: () -> Unit,
    hasButton : Boolean,
    onWorkoutClick:() ->Boolean,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp),
    ) {
        CustomCardView(
            state = state,
            backgroundImage = R.drawable.workout_svg,
            heading = R.string.workout,
            subHeading = R.string.daily_goal_workout ,
            buttonText = R.string.log_workout,
            cardColor = WorkoutRed,
            hasImage = true,
            hasButton = hasButton,
            onButtonClick = {
                onWorkoutClick()
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
                    .background(WorkoutRed)
                    .padding(vertical = 6.dp, horizontal = 18.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.workout_track,
                        state.workedOut
                    ),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }

        }


    }





}
