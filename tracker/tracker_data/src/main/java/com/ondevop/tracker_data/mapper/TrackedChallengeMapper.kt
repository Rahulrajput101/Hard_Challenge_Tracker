package com.ondevop.tracker_data.mapper

import com.ondevop.tracker_data.local.TrackedChallengeEntity
import com.ondevop.tracker_domain.model.TrackedChallenge

fun TrackedChallengeEntity.toTrackedChallenge() : TrackedChallenge {
    return TrackedChallenge(
        id =id,
        waterIntake = waterIntake,
        workedOut = workedOut,
        read = read,
        imageUri = imageUri,
        dayCount = dayCount,
        date = date
    )
}

fun TrackedChallenge.toTrackedChallengeEntity() : TrackedChallengeEntity {
    return TrackedChallengeEntity(
        id =id,
        waterIntake = waterIntake,
        workedOut = workedOut,
        read = read,
        imageUri = imageUri,
        dayCount = dayCount,
        date = date
    )
}