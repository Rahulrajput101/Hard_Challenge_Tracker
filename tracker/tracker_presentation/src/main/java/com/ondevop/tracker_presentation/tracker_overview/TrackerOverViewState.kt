package com.ondevop.tracker_presentation.tracker_overview

import java.time.LocalDate

data class TrackerOverViewState(
    val totalDays: Int = 45,
    val goal: Int = 75,
    val localDate: LocalDate = LocalDate.now(),
)
