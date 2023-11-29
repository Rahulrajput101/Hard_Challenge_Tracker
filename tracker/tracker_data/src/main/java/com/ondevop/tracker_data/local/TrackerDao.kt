package com.ondevop.tracker_data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackerDao {

    @Upsert
    fun upsertTrackedChallenge(trackedChallengeEntity: TrackedChallengeEntity): Long

    @Delete
    fun removeTrackedChallenge( trackedChallengeEntity: TrackedChallengeEntity)

    @Query("select * from TrackedChallengeEntity order by date desc")
    fun getAllTrackedChallenge() : Flow<List<TrackedChallengeEntity>>

    @Query("delete from TrackedChallengeEntity")
    suspend fun clear()
}