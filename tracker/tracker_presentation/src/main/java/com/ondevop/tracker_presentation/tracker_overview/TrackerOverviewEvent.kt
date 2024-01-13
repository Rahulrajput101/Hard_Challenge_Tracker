package com.ondevop.tracker_presentation.tracker_overview

sealed class TrackerOverviewEvent{

    object OnPreviousDayClick : TrackerOverviewEvent()
    object OnNextDayClick: TrackerOverviewEvent()
     object OnDrinkClick : TrackerOverviewEvent()
    object OnWorkoutClick: TrackerOverviewEvent()
    data class OnReadClick(val read: Boolean): TrackerOverviewEvent()
    data class OnPhotoClick(val imageUri : String): TrackerOverviewEvent()
}
