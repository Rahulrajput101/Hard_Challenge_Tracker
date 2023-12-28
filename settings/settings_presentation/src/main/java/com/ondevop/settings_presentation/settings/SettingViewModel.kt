package com.ondevop.settings_presentation.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SettingViewModel@Inject constructor(

) : ViewModel(){

    private val _notificationState = MutableStateFlow(false)
    val state = _notificationState.asStateFlow()


    fun onEvent(event: SettingEvent){
        when(event){
            SettingEvent.ToggleNotificationPermission -> {

            }
        }
    }

}