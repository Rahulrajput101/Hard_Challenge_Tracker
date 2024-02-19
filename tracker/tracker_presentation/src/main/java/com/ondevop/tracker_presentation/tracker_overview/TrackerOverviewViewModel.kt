package com.ondevop.tracker_presentation.tracker_overview

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ondevop.core_domain.model.TrackedChallenge
import com.ondevop.core_domain.prefernces.Preferences
import com.ondevop.core_domain.uitl.Constant
import com.ondevop.core_domain.uitl.Permission
import com.ondevop.core_domain.uitl.UiEvent
import com.ondevop.core_domain.uitl.UiText
import com.ondevop.core_domain.use_cases.CreateTempPathForImage
import com.ondevop.core_domain.use_cases.SaveImage
import com.ondevop.core_domain.use_cases.SaveImageBitmap
import com.ondevop.tracker_domain.use_cases.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val saveImage: SaveImage,
    private val createTempPathForImage: CreateTempPathForImage,
    private val preferences: Preferences
) : ViewModel() {

    private val _state = MutableStateFlow(TrackerOverViewState())
    val state = _state.asStateFlow()

    private val _selectedDayIsFirstDay = MutableStateFlow(false)
    val selectedDayIsFirstDay = _selectedDayIsFirstDay.asStateFlow()

    val visiblePermissionDialogQueue = mutableStateListOf<Permission>()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var trackedChallengeJob: Job? = null

    val totalDays = trackerUseCases.getAllTrackedChallenge().map {
        it.size
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    private val isYesterdayChallengeNotTracked = trackerUseCases.isYesterdayChallengeNotTracked()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val checkUserEnteredAnyData = trackerUseCases.checkUserEnteredAnyData()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val _currentDate = MutableStateFlow(LocalDate.now())
    val currentDate = _currentDate.asStateFlow()

    val challengeGoal = preferences.getGoal()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constant.Default_DAYS_GOAL)

    val isYesterdayChallengeDataMissing = combine(
        isYesterdayChallengeNotTracked,
        checkUserEnteredAnyData
    ) { noTrackedDataOfYesterday, isUserEnteredData ->
        noTrackedDataOfYesterday
        //   || !isUserEnteredData
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    /**
     * This state flow representing whether there is any tracked challenge data available in the tracker use cases
     * that is before the current date.
     */
    val isLeftDataAvailable = combine(
        trackerUseCases.getAllTrackedChallenge(),
        _currentDate
    ) { trackerChallenges, currentDate ->
        var isAvailable = false
        for (challenges in trackerChallenges) {
            isAvailable = challenges.date.isBefore(currentDate)
            if (isAvailable)
                break
        }
        isAvailable
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

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
                _currentDate.update {
                    it.plusDays(1)
                }
                _selectedDayIsFirstDay.update { false }
                fetchTrackedChallengeData()
            }

            TrackerOverviewEvent.OnPreviousDayClick -> {
                viewModelScope.launch {
                    _currentDate.update {
                        it.minusDays(1)
                    }
                    fetchTrackedChallengeData()
                    // trackerUseCases.checkTheDateIsInRange(range,)

//                    val range = totalDays.first()
//                    trackerUseCases.checkTheDateIsInRange(range, _currentDate.value).onSuccess {
//
//                    }.onFailure {
//                        if (it is CannotNavigateException){
//                            _selectedDayIsFirstDay.update { true }
//                            _uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(com.ondevop.core_domain.R.string.cannot_navigate_beyond_this)))
//                        }else{
//                            _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(it.message.toString())))
//                        }
//                    }
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
                    date = _currentDate.value,
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

            CompleteDialogEvent.OnCompleteNow -> {
                _state.update {
                    it.copy(
                        id = null,
                        waterIntake = 0,
                        imageUri = null,
                        workedOut = 0,
                        read = false,
                    )
                }
                _currentDate.update {
                    it.minusDays(1)
                }
                updateTrackedData()
            }
        }

    }


    private fun fetchTrackedChallengeData() {
        trackedChallengeJob?.cancel()
        trackerUseCases.getTrackedDataForDate(_currentDate.value)
            .onEach { trackedChallenge ->
                trackedChallenge?.let { innerTrackedChallenge ->
                    _state.update {
                        it.copy(
                            id = innerTrackedChallenge.id,
                            waterIntake = innerTrackedChallenge.waterIntake,
                            imageUri = innerTrackedChallenge.imageUri,
                            workedOut = innerTrackedChallenge.workedOut,
                            read = innerTrackedChallenge.read,
                        )
                    }
                    _currentDate.update {
                        innerTrackedChallenge.date
                    }
                } ?: run {
                    if (_currentDate.value == LocalDate.now()) {
                        _state.update {
                            TrackerOverViewState()
                        }
                    }
                }

            }.launchIn(viewModelScope)
    }

    fun permissionHandleEvent(event: PermissionHandleEvent) {
        when (event) {
            PermissionHandleEvent.DismissDialog -> {
                visiblePermissionDialogQueue.removeFirst()
            }

            is PermissionHandleEvent.OnPermissionResult -> {
                if (!event.isGranted && !visiblePermissionDialogQueue.contains(event.permission)) {
                    visiblePermissionDialogQueue.add(event.permission)
                } else {
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.Success)
                    }
                }
            }
        }
    }

    suspend fun createTempImagePath() : String {
           return createTempPathForImage() ?: ""
    }


}