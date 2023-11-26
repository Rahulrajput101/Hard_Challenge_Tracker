package com.ondevop.tracker_data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackedChallengeEntity(
    @PrimaryKey val id: Int? = null,
    val waterIntake: Int,
    val workedOut: Int,
    val read: Boolean,
    val imageUri: String? = null,
    val dayCount : Int,
    val date: Long = System.currentTimeMillis()
)
