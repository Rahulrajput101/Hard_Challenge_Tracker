package com.ondevop.core_data.mapper

import android.util.Log
import com.ondevop.core_data.local.entity.TrackedChallengeEntity
import com.ondevop.core_domain.model.TrackedChallenge
import java.time.LocalDate

fun TrackedChallengeEntity.toTrackedChallenge() : TrackedChallenge {
    Log.d("date"," date is ${ convertLongToLocalDate(date)}")
    return TrackedChallenge(
        id =id,
        waterIntake = waterIntake,
        workedOut = workedOut,
        read = read,
        imageUri = imageUri,
        date = convertLongToLocalDate(date)
    )
}

fun TrackedChallenge.toTrackedChallengeEntity() : TrackedChallengeEntity {
  //  Log.d("date"," date is ${covertLocalDateToLong(date)}")
    return TrackedChallengeEntity(
        id =id,
        waterIntake = waterIntake,
        workedOut = workedOut,
        read = read,
        imageUri = imageUri,
        date = covertLocalDateToLong(date)
    )
}
fun convertLongToLocalDate(longValue: Long): LocalDate {
    return LocalDate.ofEpochDay(longValue)
}

fun covertLocalDateToLong(localDate: LocalDate): Long{
    return localDate.toEpochDay()
}
