package com.ondevop.core.data.mapper

import com.ondevop.core.data.local.entity.TrackedChallengeEntity
import com.ondevop.tracker_domain.model.TrackedChallenge
import java.time.LocalDate

fun TrackedChallengeEntity.toTrackedChallenge() : TrackedChallenge {
    return TrackedChallenge(
        id =id,
        waterIntake = waterIntake,
        workedOut = workedOut,
        read = read,
        imageUri = imageUri,
        dayCount = dayCount,
        date = convertLongToLocalDate(date)
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
        date = covertLocalDateToLong(date)
    )
}
fun convertLongToLocalDate(longValue: Long): LocalDate {
    return LocalDate.ofEpochDay(longValue)
}

fun covertLocalDateToLong(localDate: LocalDate): Long{
    return localDate.toEpochDay()
}
