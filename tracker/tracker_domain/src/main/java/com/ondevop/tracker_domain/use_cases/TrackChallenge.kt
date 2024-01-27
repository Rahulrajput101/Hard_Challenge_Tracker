package com.ondevop.tracker_domain.use_cases

import com.ondevop.core_domain.model.TrackedChallenge
import com.ondevop.core_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.firstOrNull

class TrackChallenge(
    private val repository: TrackerRepository
) {
    /**
     * Invokes the use case to track a challenge.
     * If a record with the same date exists, updates it; otherwise, inserts a new record.
     *
     * @param trackedChallenge The tracked challenge to be inserted or updated.
     */
    suspend operator fun invoke(
        trackedChallenge: TrackedChallenge
    ){
        val existingEntity = repository.getTrackedDataForDate(trackedChallenge.date).firstOrNull()

        if (existingEntity == null) {
            repository.upsertTrackedChallenge(trackedChallenge)
        } else {
            val updatedEntity = trackedChallenge.copy(id = existingEntity.id)
            repository.upsertTrackedChallenge(updatedEntity)
        }
    }
}