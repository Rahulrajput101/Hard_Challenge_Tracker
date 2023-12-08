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
    Log.d("tovs", "${state.waterIntake}")
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = spacing.spaceMedium)
    ) {
        item {

            TrackerHeader(
                state = state
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            WaterCardView(
                modifier = Modifier.padding(
                    horizontal = spacing.spaceMedium,
                ),
                state = state,
                hasButton = state.waterIntake < state.drinkGoal,
                onDrinkClick = {
                    if (state.waterIntake < state.drinkGoal) {
                        viewModel.onEvent(TrackerOverviewEvent.OnDrinkClick)
                    }
                    return@WaterCardView state.waterIntake >= state.drinkGoal-1
                },
                onCardClick = {

                })

            WorkoutCardView(
                modifier = Modifier.padding(
                    horizontal = spacing.spaceMedium,
                ),
                state = state,
                onCardClick = {

                },
                hasButton = state.workedOut < state.workoutGoal,
                onWorkoutClick = {
                    if (state.workedOut < state.workoutGoal) {
                        viewModel.onEvent(TrackerOverviewEvent.OnWorkoutClick)
                    }
                    return@WorkoutCardView state.workedOut >= state.workoutGoal-1
                })

            ReadingCardView(
                modifier = Modifier.padding(
                    horizontal = spacing.spaceMedium,
                ),
                state = state,
                onCardClick = {

                },
                hasButton = !state.read,
                onReadClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnReadClick(true))
                    return@ReadingCardView true
                }
            )

            PictureCardView(
                modifier = Modifier.padding(
                    horizontal = spacing.spaceMedium,
                ),
                state = state,
                onCardClick = {

                },
                hasButton =state.imageUri.isNullOrEmpty(),
                onTakePictureClick = {
                    //   viewModel.onEvent(TrackerOverviewEvent.OnPhotoClick())
                   return@PictureCardView state.imageUri != null
                }
            )

            DietCardView(
                modifier = Modifier.padding(
                    horizontal = spacing.spaceMedium,
                ),
                state = state,
                onCardClick = {

                }
            )
        }


    }


}

