package com.ondevop.tracker_presentation.tracker_overview

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.ondevop.core_domain.uitl.Permission
import com.ondevop.core_domain.uitl.UiEvent
import com.ondevop.core_ui.LocalSpacing
import com.ondevop.core_ui.composables.CameraPermissionTextProvider
import com.ondevop.core_ui.composables.PermissionDialog
import com.ondevop.tracker_presentation.tracker_overview.component.CompleteDialog
import com.ondevop.tracker_presentation.tracker_overview.component.DaySelector
import com.ondevop.tracker_presentation.tracker_overview.component.DietCardView
import com.ondevop.tracker_presentation.tracker_overview.component.PhotoOptionDialog
import com.ondevop.tracker_presentation.tracker_overview.component.PictureCardView
import com.ondevop.tracker_presentation.tracker_overview.component.ReadingCardView
import com.ondevop.tracker_presentation.tracker_overview.component.TaskIncompleteDialog
import com.ondevop.tracker_presentation.tracker_overview.component.TrackerHeader
import com.ondevop.tracker_presentation.tracker_overview.component.WaterCardView
import com.ondevop.tracker_presentation.tracker_overview.component.WorkoutCardView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TrackerOverViewScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: TrackerOverviewViewModel = hiltViewModel(),
    onMenuItemClick: () -> Unit,
    onShouldShowPermissionRationale: (String) -> Boolean,
    openAppSetting: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val dialogQueue = viewModel.visiblePermissionDialogQueue
    val totalDays by viewModel.totalDays.collectAsState()
    val challengeGoal by viewModel.challengeGoal.collectAsState()
    val isYesterdayChallengeDataMissing by viewModel.isYesterdayChallengeDataMissing.collectAsState()
    val selectedDayIsFirstDay by viewModel.selectedDayIsFirstDay.collectAsState()
    val isLeftDayAvailable by viewModel.isLeftDataAvailable.collectAsState()
    val currentDate by viewModel.currentDate.collectAsState()
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    var tempImgPath by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var shouldShowCompleteDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var shouldShowTaskNotCompleteDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var shouldShowPhotoOptionDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val permissionsToRequest = arrayOf(Permission.CAMERA)

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permissions ->
                viewModel.permissionHandleEvent(
                    PermissionHandleEvent.OnPermissionResult(
                        permission = permissions,
                        isGranted = perms[permissions.toPermissionString()] == true
                    )
                )
            }
        }
    )
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                viewModel.onEvent(TrackerOverviewEvent.OnPhotoClick(it.toString()))
            }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                tempImgPath?.let {
                    viewModel.onEvent(TrackerOverviewEvent.OnPhotoClick(it))
                }
            }

            shouldShowPhotoOptionDialog = false
        }
    )


    LaunchedEffect(key1 = true) {
        delay(2000)
        if (totalDays >= challengeGoal) {
            shouldShowCompleteDialog = true
        }

        if (isYesterdayChallengeDataMissing) {
            shouldShowTaskNotCompleteDialog = true
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
                onMenuItemClick = onMenuItemClick,
                onPremiumClick = {

                }
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            DaySelector(
                date = currentDate,
                onPreviousDayClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnPreviousDayClick)
                },
                onNextDayClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnNextDayClick)
                },
                isLeftAvailable = isLeftDayAvailable
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
//                    singlePhotoPickerLauncher.launch(
//                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
//                    )
                    shouldShowPhotoOptionDialog = true

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

            TaskIncompleteDialog(
                isDialogShowing = shouldShowTaskNotCompleteDialog,
                onRestart = {
                    viewModel.onDialogEvent(CompleteDialogEvent.OnRestart)
                    shouldShowTaskNotCompleteDialog = false
                },
                onCompleteNow = {
                    viewModel.onDialogEvent(CompleteDialogEvent.OnCompleteNow)
                    shouldShowTaskNotCompleteDialog = false
                },
                onDismiss = {
                    shouldShowTaskNotCompleteDialog = false
                }
            )
            PhotoOptionDialog(
                isDialogShowing = shouldShowPhotoOptionDialog,
                onCameraClick = {
                    if (context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        scope.launch {
                            val uri = viewModel.createTempImagePath()
                            tempImgPath = uri
                            cameraLauncher.launch(uri.toUri())
                        }
                    } else {
                        permissionLauncher.launch(
                            permissionsToRequest.map {
                                it.toPermissionString()
                            }.toTypedArray()
                        )
                    }
                },
                onPickerClick = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                onDismiss = {
                    shouldShowPhotoOptionDialog = false
                }
            )

            dialogQueue
                .reversed()
                .forEach { permission ->
                    PermissionDialog(
                        permissionTextProvider = when (permission) {
                            Permission.CAMERA -> CameraPermissionTextProvider()
                            else -> return@forEach
                        },
                        isPermanentlyDeclined = !onShouldShowPermissionRationale(permission.toPermissionString()),
                        onDismissClick = {
                            viewModel.permissionHandleEvent(PermissionHandleEvent.DismissDialog)
                        },
                        onOkClick = {
                            viewModel.permissionHandleEvent(PermissionHandleEvent.DismissDialog)
                            permissionLauncher.launch(
                                arrayOf(permission.toPermissionString())
                            )
                        },
                        onGoToAppSettingsClicks = openAppSetting,
                    )
                }
        }
    }

}
