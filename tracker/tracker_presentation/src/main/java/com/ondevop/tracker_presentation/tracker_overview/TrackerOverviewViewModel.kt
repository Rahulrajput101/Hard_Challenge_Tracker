package com.ondevop.tracker_presentation.tracker_overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ondevop.core.uitl.UiEvent
import com.ondevop.tracker_domain.use_cases.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    val trackerUseCases: TrackerUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(TrackerOverViewState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        trackedData(LocalDate.now())
    }


    fun onEvent(event: TrackerOverviewEvent) {
        when (event) {
            TrackerOverviewEvent.OnDrinkClick -> {

                _state.value = _state.value.copy(
                    waterIntake = state.value.waterIntake.plus(1)
                )
            }
            is TrackerOverviewEvent.OnPhotoClick -> {
               _state.value = _state.value.copy(
                   imageUri = event.imageUri
               )
            }
            is TrackerOverviewEvent.OnReadClick -> {
               _state.value = _state.value.copy(
                   read = event.read
               )
            }
            TrackerOverviewEvent.OnWorkoutClick -> {
                _state.value = _state.value.copy(
                    workedOut = _state.value.workedOut.plus(1)
                )
            }
        }
    }

    private fun trackedData(date: LocalDate): Job {
        return viewModelScope.launch {
            trackerUseCases.getTrackedDataForDate(date)
                .collectLatest { trackedChallenge ->
                    _state.value = _state.value.copy(
                        id = trackedChallenge.id,
                        totalDays = trackedChallenge.dayCount,
                        waterIntake = trackedChallenge.waterIntake,
                        workedOut = trackedChallenge.workedOut,
                        read = trackedChallenge.read,
                        imageUri = trackedChallenge.imageUri,
                        localDate = trackedChallenge.date
                    )
                }
        }
    }

}