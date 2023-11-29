package com.ondevop.tracker_domain.model

data class TrackedChallenge(
    val id: Long? = null,
    val waterIntake: Int,
    val workedOut: Int,
    val read: Boolean,
    val imageUri: String? = null,
    val dayCount : Int,
    val date: Long = System.currentTimeMillis()
)
