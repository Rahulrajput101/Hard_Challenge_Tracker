package com.ondevop.tracker_presentation.tracker_overview

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ondevop.core_domain.uitl.UiEvent
import com.ondevop.core_ui.LocalSpacing
import com.ondevop.tracker_presentation.tracker_overview.component.CompleteDialog
import com.ondevop.tracker_presentation.tracker_overview.component.DaySelector
import com.ondevop.tracker_presentation.tracker_overview.component.DietCardView
import com.ondevop.tracker_presentation.tracker_overview.component.PictureCardView
import com.ondevop.tracker_presentation.tracker_overview.component.ReadingCardView
import com.ondevop.tracker_presentation.tracker_overview.component.TrackerHeader
import com.ondevop.tracker_presentation.tracker_overview.component.WaterCardView
import com.ondevop.tracker_presentation.tracker_overview.component.WorkoutCardView
import kotlinx.coroutines.delay

@Composable
fun TrackerOverViewScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: TrackerOverviewViewModel = hiltViewModel(),
    onMenuItemClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val totalDays by viewModel.totalDays.collectAsState()
    val challengeGoal by viewModel.challengeGoal.collectAsState()
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    var shouldShowCompleteDialog by remember {
        mutableStateOf(false)
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                viewModel.onEvent(TrackerOverviewEvent.OnPhotoClick(it.toString()))
            }

        }
    )

    LaunchedEffect(key1 = true) {
        delay(2000)
        if (totalDays >= challengeGoal) {
            shouldShowCompleteDialog = true
        }
    }


    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(event.message.asString(context))
                }

                else -> Unit
            }
        }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = spacing.spaceExtraSmall)
    ) {
        item {

            TrackerHeader(
                state = state,
                challengeGoal = challengeGoal,
                totalDays = totalDays,
                onMenuItemClick = onMenuItemClick
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            DaySelector(
                date = state.localDate,
                onPreviousDayClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnPreviousDayClick)
                },
                onNextDayClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnNextDayClick)
                }
            )
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
                    return@WaterCardView state.waterIntake >= state.drinkGoal - 1
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
                    return@WorkoutCardView state.workedOut >= state.workoutGoal - 1
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
                hasButton = state.imageUri.isNullOrEmpty(),
                onTakePictureClick = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )

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


            CompleteDialog(
                isDialogShowing = shouldShowCompleteDialog,
                onRestart = {
                    viewModel.onDialogEvent(CompleteDialogEvent.OnRestart)
                    shouldShowCompleteDialog = false
                },
                onMoveForward = {
                    viewModel.onDialogEvent(CompleteDialogEvent.OnMoveForward)
                    shouldShowCompleteDialog = false
                },
                onDismiss = {
                    shouldShowCompleteDialog = false
                }
            )




        }


    }


}

