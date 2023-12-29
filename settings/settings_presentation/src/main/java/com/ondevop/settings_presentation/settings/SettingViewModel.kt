package com.ondevop.settings_presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ondevop.core.domain.prefernces.Preferences
import com.ondevop.core.uitl.UiEvent
import com.ondevop.settings_domain.use_case.SignOut
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val signOut : SignOut
) : ViewModel(){

    private val _notificationState = MutableStateFlow(false)
    val state = _notificationState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: SettingEvent){
        when(event){
            SettingEvent.ToggleNotificationPermission -> {

            }

            SettingEvent.SignOut -> {
                viewModelScope.launch {
                    signOut().onSuccess {
                        _uiEvent.send(UiEvent.Success)

                    }.onFailure {
                        it.printStackTrace()
                    }

                }

            }
        }
    }

}