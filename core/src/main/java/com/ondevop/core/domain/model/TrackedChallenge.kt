package com.ondevop.core.domain.model

import java.time.LocalDate

data class TrackedChallenge(
    val id: Long? = null,
    val waterIntake: Int,
    val workedOut: Int,
    val read: Boolean,
    val imageUri: String? = null,
    val date: LocalDate
)
