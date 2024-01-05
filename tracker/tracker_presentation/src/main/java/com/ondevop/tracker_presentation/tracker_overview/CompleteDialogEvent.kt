package com.ondevop.tracker_presentation.tracker_overview

sealed interface CompleteDialogEvent {

    object OnRestart : CompleteDialogEvent
    object OnMoveForward: CompleteDialogEvent
}