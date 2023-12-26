package com.ondevop.tracker_presentation.tracker_overview

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ondevop.core.domain.use_cases.SaveImage
import com.ondevop.core.uitl.UiEvent
import com.ondevop.core.uitl.UiText
import com.ondevop.core.domain.model.TrackedChallenge
import com.ondevop.core.domain.prefernces.Preferences
import com.ondevop.core.uitl.Constant
import com.ondevop.tracker_domain.use_cases.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val saveImage: SaveImage,
    private val preferences: Preferences
) : ViewModel() {

    private val _state = MutableStateFlow(TrackerOverViewState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

     val totalDays = trackerUseCases.getAllTrackedChallenge().map {
         it.size
     }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    val challengeGoal = preferences.getGoal().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constant.Default_DAYS_GOAL)

    val tackedChallengeData = trackerUseCases.getTrackedDataForDate(LocalDate.now())
        .onEach { trackedChallenge ->
            trackedChallenge?.let {
                _state.value = _state.value.copy(
                    id = trackedChallenge.id,
                    waterIntake = trackedChallenge.waterIntake,
                    imageUri = trackedChallenge.imageUri,
                    workedOut = trackedChallenge.workedOut,
                    read = trackedChallenge.read,
                    localDate = trackedChallenge.date
                )
            }
        }.launchIn(viewModelScope)


    fun onEvent(event: TrackerOverviewEvent) {
        when (event) {
            TrackerOverviewEvent.OnDrinkClick -> {
                _state.value = _state.value.copy(
                    waterIntake = state.value.waterIntake.plus(1)
                )
                updateTrackedData()
            }

            is TrackerOverviewEvent.OnPhotoClick -> {

                saveImageInInternalStorage(event.imageUri.toUri())
            }

            is TrackerOverviewEvent.OnReadClick -> {
                _state.value = _state.value.copy(
                    read = event.read
                )
                updateTrackedData()
            }

            TrackerOverviewEvent.OnWorkoutClick -> {
                _state.value = _state.value.copy(
                    workedOut = _state.value.workedOut.plus(1)
                )
                updateTrackedData()
            }
        }
    }

    private fun updateTrackedData() {
        viewModelScope.launch {
            trackerUseCases.trackChallenge(
                TrackedChallenge(
                    id = state.value.id,
                    waterIntake = state.value.waterIntake,
                    workedOut = state.value.workedOut,
                    read = state.value.read,
                    imageUri = state.value.imageUri,
                    date = state.value.localDate,
                )
            )
        }
    }

    private fun saveImageInInternalStorage(uri: Uri?) {
        viewModelScope.launch {
            saveImage(uri)
                .onSuccess {
                    _state.value = _state.value.copy(
                        imageUri = it
                    )
                    updateTrackedData()
                }
                .onFailure {
                    _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(it.message.toString())))
                }
        }

    }

    fun onDialogEvent(event: CompleteDialogEvent){
        when(event){
            CompleteDialogEvent.OnMoveForward -> {
                viewModelScope.launch {
                    preferences.apply {
                        saveGoal(challengeGoal.value.plus(76))
                    }
                }

            }
            CompleteDialogEvent.OnRestart -> {
                viewModelScope.launch {
                    val saveGoalDeferred = async {
                        preferences.saveGoal(75)
                    }

                    val clearTrackedDataDeferred = async {
                        trackerUseCases.clearAllTrackedData()
                    }

                    saveGoalDeferred.await()
                    clearTrackedDataDeferred.await()
                }
            }
        }

    }

}