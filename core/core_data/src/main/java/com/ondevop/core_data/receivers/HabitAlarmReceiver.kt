package com.ondevop.core_data.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.ondevop.core_domain.MyNotification
import com.ondevop.core_domain.MyNotificationManager
import com.ondevop.core_domain.use_cases.SchedulingHabitAlarm
import com.ondevop.core_domain.use_cases.ToShowNotification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class HabitAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var toShowNotification: ToShowNotification

    @Inject
    lateinit var  schedulingHabitAlarm: SchedulingHabitAlarm

    @Inject
    lateinit var myNotificationManager: MyNotificationManager

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(p0: Context?, p1: Intent?) {
        GlobalScope.launch(Dispatchers.Main) {
            val shouldShowNotification = withContext(Dispatchers.IO){
                toShowNotification(LocalDate.now())
            }

            if(shouldShowNotification){
                myNotificationManager.notify(MyNotification.HabitNotification)
            }

        }

    }

}