package com.ondevop.tracker_presentation.tracker_overview

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ondevop.core_domain.model.TrackedChallenge
import com.ondevop.core_domain.prefernces.Preferences
import com.ondevop.core_domain.uitl.Constant
import com.ondevop.core_domain.uitl.UiEvent
import com.ondevop.core_domain.uitl.UiText
import com.ondevop.core_domain.use_cases.SaveImage
import com.ondevop.tracker_domain.use_cases.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val _selectedDayIsFirstDay = MutableStateFlow(false)
    val selectedDayIsFirstDay= _selectedDayIsFirstDay.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var trackedChallengeJob: Job? = null

    val totalDays = trackerUseCases.getAllTrackedChallenge().map {
        it.size
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    val isYesterdayChallengeNotTracked = trackerUseCases.isYesterdayChallengeNotTracked()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val checkUserEnteredAnyData = trackerUseCases.checkUserEnteredAnyData()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)


    val challengeGoal = preferences.getGoal()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constant.Default_DAYS_GOAL)

    val isYesterdayChallengeDataMissing = combine(isYesterdayChallengeNotTracked, checkUserEnteredAnyData){ noTrackedDataOfYesterday,isUserEnteredData ->
         noTrackedDataOfYesterday || !isUserEnteredData
    } .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)


    init {
        fetchTrackedChallengeData()
        viewModelScope.launch {
            trackerUseCases.hasUserLostTheChallenge()
        }

    }

    fun onEvent(event: TrackerOverviewEvent) {
        when (event) {
            TrackerOverviewEvent.OnDrinkClick -> {
                _state.update {
                    it.copy(
                        waterIntake = state.value.waterIntake.plus(1)
                    )
                }
                updateTrackedData()
            }

            is TrackerOverviewEvent.OnPhotoClick -> {

                saveImageInInternalStorage(event.imageUri.toUri())
            }

            is TrackerOverviewEvent.OnReadClick -> {
                _state.update {
                    it.copy(
                        read = event.read
                    )
                }
                updateTrackedData()
            }

            TrackerOverviewEvent.OnWorkoutClick -> {
                _state.update {
                    it.copy(
                        workedOut = _state.value.workedOut.plus(1)
                    )
                }
                updateTrackedData()

            }

            TrackerOverviewEvent.OnNextDayClick -> {
                _state.update {
                    it.copy(
                        localDate = state.value.localDate.plusDays(1)
                    )
                }
                _selectedDayIsFirstDay.update { false }
                fetchTrackedChallengeData()
            }

            TrackerOverviewEvent.OnPreviousDayClick -> {
                viewModelScope.launch {
                    val range = totalDays.first()
                    trackerUseCases.checkTheDateIsInRange(range, state.value.localDate).onSuccess {
                        _state.update {
                            it.copy(
                                localDate = state.value.localDate.minusDays(1)
                            )
                        }
                        fetchTrackedChallengeData()
                    }.onFailure {
                        _selectedDayIsFirstDay.update { true }
                        _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(it.message.toString())))
                    }
                }
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
                .onSuccess { imageUri ->
                    _state.update {
                        it.copy(
                            imageUri = imageUri
                        )
                    }

                    updateTrackedData()
                }
                .onFailure {
                    _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(it.message.toString())))
                }
        }

    }

    fun onDialogEvent(event: CompleteDialogEvent) {
        when (event) {
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


    private fun fetchTrackedChallengeData() {
        trackedChallengeJob?.cancel()
        trackerUseCases.getTrackedDataForDate(state.value.localDate)
            .onEach { trackedChallenge ->
                trackedChallenge?.let { innerTrackedChallenge ->
                    _state.update {
                        it.copy(
                            id = innerTrackedChallenge.id,
                            waterIntake = innerTrackedChallenge.waterIntake,
                            imageUri = innerTrackedChallenge.imageUri,
                            workedOut = innerTrackedChallenge.workedOut,
                            read = innerTrackedChallenge.read,
                            localDate = innerTrackedChallenge.date
                        )
                    }
                } ?: run {
                    if (state.value.localDate == LocalDate.now()) {
                        _state.update {
                            TrackerOverViewState()
                        }
                    }
                }

            }.launchIn(viewModelScope)
    }

}