package com.ondevop.tracker_data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert

@Dao
interface TrackerDao {

    @Upsert
    fun upsertTrackedChallenge(trackedChallengeEntity: TrackedChallengeEntity): Long

    @Delete
    fun removeTrackedChallenge( trackedChallengeEntity: TrackedChallengeEntity)
}