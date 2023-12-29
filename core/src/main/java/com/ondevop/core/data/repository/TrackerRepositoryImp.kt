package com.ondevop.core.data.repository

import com.ondevop.core.data.local.dao.TrackerDao
import com.ondevop.core.data.mapper.toTrackedChallenge
import com.ondevop.core.data.mapper.toTrackedChallengeEntity
import com.ondevop.core.domain.model.TrackedChallenge
import com.ondevop.core.domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImp(
    private val trackerDao: TrackerDao,
) : TrackerRepository {
    override suspend fun upsertTrackedChallenge(trackedChallenge: TrackedChallenge): Long {
        return trackerDao.upsertTrackedChallenge(
            trackedChallenge.toTrackedChallengeEntity()
        )
    }

    override fun getTrackedDataForDate(date: LocalDate): Flow<TrackedChallenge?> {
        return trackerDao.getTrackedDataForDate(date.toEpochDay()).map {
            it?.toTrackedChallenge()
        }
    }

    override fun getAllTrackedChallenge(): Flow<List<TrackedChallenge>> {
        return trackerDao.getAllTrackedChallenge().map { trackedChallenges ->
            trackedChallenges.map {
                it.toTrackedChallenge()
            }
        }
    }

    override suspend fun clearAllTrackedChallenge() {
        return trackerDao.clear()
    }
}