package com.ondevop.core_domain.uitl

sealed class UiEvent {

     object Success: UiEvent()
    data class ShowSnackbar(val message : UiText): UiEvent()
    object NavigateUp: UiEvent()
}

