package com.ondevop.tracker_domain.use_cases

import com.ondevop.core_domain.model.TrackedChallenge
import com.ondevop.core_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class HasUserLostTheChallenge(
    private val repository: TrackerRepository
) {
    /**
     * Retrieves tracked data for the date two days ago and maps the result to
     * a Flow of Boolean values.
     *
     * @return Flow<Boolean> - True if tracked data is available, false otherwise.
     */
    operator fun invoke() : Flow<Boolean> {
        val ereYesterday = LocalDate.now().minusDays(2)
        return repository.getTrackedDataForDate(ereYesterday).map { trackedChallenge ->
            trackedChallenge != null
        }

    }
}