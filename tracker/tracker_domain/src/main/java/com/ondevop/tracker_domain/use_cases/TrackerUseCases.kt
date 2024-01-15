package com.ondevop.tracker_domain.use_cases


data class TrackerUseCases (
    val trackChallenge: TrackChallenge,
    val getTrackedDataForDate: GetTrackedDataForDate,
    val getAllTrackedChallenge: GetAllTrackedChallenge,
    val getTrackedChallengeCount: GetTrackedChallengeCount,
    val filterADayChallenge: FilterADayChallenge,
    val clearAllTrackedData: ClearAllTrackedData,

    val checkTheDateIsInRange: CheckTheDateIsInRange
)