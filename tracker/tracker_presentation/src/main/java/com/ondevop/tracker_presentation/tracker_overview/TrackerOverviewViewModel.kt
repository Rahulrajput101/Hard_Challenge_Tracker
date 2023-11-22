package com.ondevop.tracker_presentation.tracker_overview

import androidx.lifecycle.ViewModel
import com.ondevop.core.uitl.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(TrackerOverViewState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
}