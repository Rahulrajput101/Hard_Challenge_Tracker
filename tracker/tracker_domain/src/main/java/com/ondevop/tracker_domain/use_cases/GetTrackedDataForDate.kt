package com.ondevop.tracker_domain.use_cases

import com.ondevop.tracker_domain.model.TrackedChallenge
import com.ondevop.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetTrackedDataForDate(
    private val repository: TrackerRepository
) {
    operator fun invoke(date: LocalDate) : Flow<TrackedChallenge> {
        return repository.getTrackedDataForDate(date)
    }
}