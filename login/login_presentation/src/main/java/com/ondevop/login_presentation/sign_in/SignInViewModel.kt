package com.ondevop.login_presentation.sign_in

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ondevop.core.domain.prefernces.Preferences
import com.ondevop.login_domain.use_case.SignUpWithEmailAndPassword
import com.ondevop.core.R
import com.ondevop.core.uitl.UiEvent
import com.ondevop.core.uitl.UiText
import com.ondevop.login_domain.use_case.ValidateEmail
import com.ondevop.login_domain.use_case.ValidatePassword
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
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    private val preferences: Preferences
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignInClick -> {
                viewModelScope.launch {
                 signIn()
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
        val emailResult = validateEmail(state.value.email)
        val passwordResult = validatePassword(state.value.password)
        val hasError = listOf(
            emailResult,
            passwordResult,
        ).any { !it.successful }

        if(hasError){
            _state.value = _state.value.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
            return
        }

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