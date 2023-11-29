package com.ondevop.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ondevop.core_ui.LocalSpacing
import com.ondevop.tracker_presentation.tracker_overview.component.DietCardView
import com.ondevop.tracker_presentation.tracker_overview.component.PictureCardView
import com.ondevop.tracker_presentation.tracker_overview.component.ReadingCardView
import com.ondevop.tracker_presentation.tracker_overview.component.TrackerHeader
import com.ondevop.tracker_presentation.tracker_overview.component.WaterCardView
import com.ondevop.tracker_presentation.tracker_overview.component.WorkoutCardView

@Composable
fun TrackerOverViewScreen(
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val spacing = LocalSpacing.current

   LazyColumn(
       modifier = Modifier
           .fillMaxWidth()
           .padding(bottom = spacing.spaceMedium)
   ){
       item {
           TrackerHeader(
               state = state
           )
           Spacer(modifier = Modifier.height(spacing.spaceMedium))
           WaterCardView(
               modifier = Modifier.padding(
                   horizontal = spacing.spaceMedium,
               ),
               drinkWater = 2,
               waterGoal = 3,
               onDrinkClick = {

               },
               onCardClick = {

               })

           WorkoutCardView(
               modifier = Modifier.padding(
                   horizontal = spacing.spaceMedium,
               ),
               totalWorkout = 0,
               workoutGoal = 2,
               workoutTime = "45min",
               onCardClick = {

               },
               onWorkoutClick = {

               })

           ReadingCardView(
               modifier = Modifier.padding(
                   horizontal = spacing.spaceMedium,
               ),
               onCardClick = {

               },
               onReadClick = {

               }
           )

           PictureCardView(
               modifier = Modifier.padding(
                   horizontal = spacing.spaceMedium,
               ),
               onCardClick = {

               },
               onTakePictureClick = {

               }
           )

           DietCardView(
               modifier = Modifier.padding(
                   horizontal = spacing.spaceMedium,
               ),
               onCardClick = {

               }
           )
       }


   }



}
