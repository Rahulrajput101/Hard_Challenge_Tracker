package com.ondevop.login_presentation.sign_up

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ondevop.core_domain.prefernces.Preferences
import com.ondevop.core_domain.uitl.UiEvent
import com.ondevop.core_domain.R
import com.ondevop.core_domain.uitl.UiText
import com.ondevop.login_domain.use_case.SignUpWithEmailAndPassword
import com.ondevop.login_domain.use_case.ValidateEmail
import com.ondevop.login_domain.use_case.ValidatePassword
import com.ondevop.login_domain.use_case.ValidateRepeatedPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val signUpWithEmailAndPassword: SignUpWithEmailAndPassword,
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validateRepeatedPassword: ValidateRepeatedPassword,
    private val preferences: Preferences
) : ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnCreateClick -> {
                viewModelScope.launch {
                    if(state.value.name.isNotBlank()){
                        signUp()
                    }else{
                        _uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.please_enter_your_name)))
                    }
                }
            }

            is SignUpEvent.UpdateEmail -> {
                _state.value = _state.value.copy(email = event.email)
            }

            is SignUpEvent.UpdatePassword -> {
                _state.value = _state.value.copy(password = event.password)
            }

            is SignUpEvent.UpdateRepeatedPassword -> {
                _state.value = _state.value.copy(repeatedPassword = event.repeatedPassword)
            }

            is SignUpEvent.UpdateName -> {
                _state.value = _state.value.copy(name = event.name)
            }
            is SignUpEvent.UpdateProfileUir -> {
                _state.value = _state.value.copy(profileUri = event.profileUri)
            }
        }
    }

    private fun signUp() {
        val emailResult = validateEmail(state.value.email)
        val passwordResult = validatePassword(state.value.password)
        val repeatedPasswordResult = validateRepeatedPassword(state.value.password,state.value.repeatedPassword)

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult
        ).any { !it.successful }

        if(hasError){
            _state.value = _state.value.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage
            )
            return
        }

        viewModelScope.launch {
            signUpWithEmailAndPassword(state.value.email, state.value.password,state.value.name,state.value.profileUri)
                .onSuccess {
                    _uiEvent.send(UiEvent.Success)
                    preferences.apply {
                        saveLoggedInfo(true)
                        saveUserName(state.value.name)
                        saveProfileUri(state.value.profileUri.toString())
                    }
                    _uiEvent.send(UiEvent.Success)
                }
                .onFailure {
                    _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(it.message.toString())))
                }
        }

    }


}