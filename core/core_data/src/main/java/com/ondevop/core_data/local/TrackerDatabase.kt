package com.ondevop.core_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ondevop.core_data.local.dao.TrackerDao
import com.ondevop.core_data.local.entity.TrackedChallengeEntity

@Database(
    entities = [TrackedChallengeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TrackerDatabase : RoomDatabase() {
    abstract val trackerDao: TrackerDao
    companion object {
        const val DATABASE_NAME = "tracker_database"
    }
}