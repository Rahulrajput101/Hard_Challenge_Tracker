package com.ondevop.settings_presentation.settings

sealed interface SettingEvent{

    object ToggleNotificationPermission : SettingEvent
    object SignOut: SettingEvent
}