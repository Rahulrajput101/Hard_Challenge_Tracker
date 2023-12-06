package com.ondevop.tracker_presentation.tracker_overview

import android.util.Log
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
    Log.d("tovs","${state.waterIntake}")
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
               drinkWater = state.waterIntake,
               waterGoal = state.drinkGoal,
               onDrinkClick = {
                 if (state.waterIntake < state.drinkGoal){
                     viewModel.onEvent(TrackerOverviewEvent.OnDrinkClick)
                 }
               },
               onCardClick = {

               })

           WorkoutCardView(
               modifier = Modifier.padding(
                   horizontal = spacing.spaceMedium,
               ),
               totalWorkout = state.workedOut,
               workoutGoal = state.workoutGoal,
               workoutTime = "45min",
               onCardClick = {

               },
               onWorkoutClick = {
                   if(state.workedOut < state.workoutGoal){
                       viewModel.onEvent(TrackerOverviewEvent.OnWorkoutClick)
                   }
               })

           ReadingCardView(
               modifier = Modifier.padding(
                   horizontal = spacing.spaceMedium,
               ),
               onCardClick = {

               },
               onReadClick = {
                 viewModel.onEvent(TrackerOverviewEvent.OnReadClick(true))
               }
           )

           PictureCardView(
               modifier = Modifier.padding(
                   horizontal = spacing.spaceMedium,
               ),
               onCardClick = {

               },
               onTakePictureClick = {
               //   viewModel.onEvent(TrackerOverviewEvent.OnPhotoClick())
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

