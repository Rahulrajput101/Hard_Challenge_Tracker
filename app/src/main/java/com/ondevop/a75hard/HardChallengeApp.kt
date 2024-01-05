package com.ondevop.a75hard

import android.app.Application
import com.ondevop.core_domain.MyNotificationChannel
import com.ondevop.core_domain.MyNotificationManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class HardChallengeApp : Application() {

    @Inject
    lateinit var myNotificationManager: MyNotificationManager

    override fun onCreate() {
        super.onCreate()
        myNotificationManager.createChannel(MyNotificationChannel.HabitReminderChannel)

    }


}