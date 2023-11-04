package com.ondevop.core.uitl

sealed class UiEvent {

     object Success: UiEvent()
    data class ShowSnackbar(val message : UiText): UiEvent()
    object NavigateUp: UiEvent()
}

