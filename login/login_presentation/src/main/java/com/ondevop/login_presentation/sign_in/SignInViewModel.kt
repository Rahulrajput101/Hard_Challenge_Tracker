package com.ondevop.login_presentation.sign_in

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ondevop.core.domain.prefernces.Preferences
import com.ondevop.login_domain.use_case.SignUpWithEmailAndPassword
import com.ondevop.core.R
import com.plcoding.core.util.UiEvent
import com.plcoding.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    val signInWithEmailPassword: SignUpWithEmailAndPassword,
    val preferences: Preferences
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignInClick -> {
                viewModelScope.launch {
                    if (state.value.email.isNotEmpty() && state.value.password.isNotEmpty()) {
                        signIn()
                    } else {
                        Log.d("MyTag","suvm: else")
                      _uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.please_enter_all_the_fields)))
                    }
                }
            }

            is SignInEvent.UpdateEmail -> {
                _state.value = _state.value.copy(email = event.email)
            }

            is SignInEvent.UpdatePassword -> {
                _state.value = _state.value.copy(password = event.password)
            }
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            signInWithEmailPassword(state.value.email, state.value.password)
                .onSuccess {
                    _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(it)))
                    preferences.saveLoggedInfo(true)
                    _uiEvent.send(UiEvent.Success)
                    Log.d("MyTag","suvm: ${preferences.getLoggedInfo()}")
                }
                .onFailure {
                    _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(it.message.toString())))
                }
        }

    }

}