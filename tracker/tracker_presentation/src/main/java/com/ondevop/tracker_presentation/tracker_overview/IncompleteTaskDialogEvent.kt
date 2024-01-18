package com.ondevop.tracker_presentation.tracker_overview

sealed interface IncompleteTaskDialogEvent {
    object OnRestart : IncompleteTaskDialogEvent
    object OnCompleteNow: IncompleteTaskDialogEvent
}