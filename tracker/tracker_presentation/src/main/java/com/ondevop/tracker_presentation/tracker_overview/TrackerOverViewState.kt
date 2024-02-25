package com.ondevop.tracker_presentation.tracker_overview

import java.time.LocalDate

data class TrackerOverViewState(
    val id: Long? = null,
    val drinkGoal: Int = 4,
    val waterIntake : Int = 0,
    val workedOut : Int = 0,
    val workoutGoal: Int = 2,
    val read: Boolean = false,
    val imageUri: String? = null,
)
