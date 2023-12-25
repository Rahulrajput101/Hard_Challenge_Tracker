package com.ondevop.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackedChallengeEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Long? = null,
    val waterIntake: Int,
    val workedOut: Int,
    val read: Boolean,
    val imageUri: String? = null,
    val date: Long
)
