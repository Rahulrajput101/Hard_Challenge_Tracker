package com.ondevop.tracker_domain.use_cases


import com.ondevop.core_domain.model.TrackedChallenge
import com.ondevop.core_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class IsYesterdayChallengeTracked (
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(yesterdayDate: LocalDate) : Boolean? {
        return try {
            repository.getTrackedDataForDate(yesterdayDate)
                .map { trackedChallenge ->
                    trackedChallenge != null
                }
                .firstOrNull()
        } catch (e: Exception) {
            null
        }
    }
}