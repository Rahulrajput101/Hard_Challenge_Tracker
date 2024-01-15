package com.ondevop.core_data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.ondevop.core_data.local.entity.TrackedChallengeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackerDao {

    @Upsert
   suspend fun upsertTrackedChallenge(trackedChallengeEntity: TrackedChallengeEntity): Long

    @Delete
    fun removeTrackedChallenge( trackedChallengeEntity: TrackedChallengeEntity)

    @Query("select * from TrackedChallengeEntity WHERE date = :date")
    fun getTrackedDataForDate(date: Long) : Flow<TrackedChallengeEntity?>

    @Query("select * from TrackedChallengeEntity order by date desc")
    fun getAllTrackedChallenge() : Flow<List<TrackedChallengeEntity>>

    @Query("select count(*) from TrackedChallengeEntity")
    fun getTrackedChallengeCount() : Flow<Int>

    @Query("delete from TrackedChallengeEntity")
    suspend fun clear()
}