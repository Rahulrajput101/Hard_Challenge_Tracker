package com.ondevop.core.domain

interface MyNotificationManager {
    fun notify(myNotification: MyNotification)
    fun createChannel(channel: MyNotificationChannel)
}
sealed class MyNotification(val notificationId: Int, val channel: MyNotificationChannel) {
    object HabitNotification: MyNotification(2, MyNotificationChannel.HabitReminderChannel)
}

sealed class MyNotificationChannel(val id: String, val channelName: String) {
    object HabitReminderChannel : MyNotificationChannel("habit_reminder", "habit Reminder")
}

//enum class MyNotificationChannelEnum(val id: String, val channelName: String) {
//    DrinkReminderChannel("habit_reminder", "habit Reminder")
//}
