package com.ondevop.login_presentation.sign_in

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ondevop.core_domain.prefernces.Preferences
import com.ondevop.core_domain.uitl.UiEvent
import com.ondevop.core_domain.uitl.UiText
import com.ondevop.login_domain.use_case.IsSignedIn
import com.ondevop.login_domain.use_case.SignInWithEmailAndPassword
import com.ondevop.login_domain.use_case.SignInWithGoogle
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
    val signInWithEmailPassword: SignInWithEmailAndPassword,
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    private val preferences: Preferences,
    private val signInWithGoogle: SignInWithGoogle,
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignInClick -> {
                signInWithEmailAndPass()
            }

            is SignInEvent.UpdateEmail -> {
                _state.value = _state.value.copy(email = event.email)
            }

            is SignInEvent.UpdatePassword -> {
                _state.value = _state.value.copy(password = event.password)
            }

            is SignInEvent.SignInUnsuccessful -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(isLoading = false)
                    _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(event.errorMessage)))
                }
            }

            is SignInEvent.SaveUserdata -> {
                viewModelScope.launch {
                    preferences.saveUserName(event.userData.userName ?: "")
                    if(event.userData.profilePictureUrl != null){
                        preferences.saveProfileUri(event.userData.profilePictureUrl)
                    }
                    _uiEvent.send(UiEvent.Success)
                }
            }

            is SignInEvent.SignInWithGoogle -> {
                   viewModelScope.launch {
                       _state.value = _state.value.copy(isLoading = true)
                      signInWithGoogle(event.idToken).onSuccess {
                          preferences.saveLoggedInfo(true)
                          preferences.saveUserName(it.userName)
                          val profileUri = it.profileUri.toString()
                          preferences.saveProfileUri(profileUri)
                          _uiEvent.send(UiEvent.Success)
                      }.onFailure {
                          _state.value = _state.value.copy(isLoading = false)
                          _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(it.message.toString())))
                      }
                   }
            }
        }
    }

    private fun signInWithEmailAndPass() {
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
            _state.value = _state.value.copy(isLoading = true)
            signInWithEmailPassword(state.value.email, state.value.password)
                .onSuccess {
                    preferences.saveLoggedInfo(true)
                    preferences.saveUserName(it.userName)
                    val profileUri = it.profileUri.toString()
                    preferences.saveProfileUri(profileUri)
                    _uiEvent.send(UiEvent.Success)
                }
                .onFailure {
                    _state.value = _state.value.copy(isLoading = false)
                    _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(it.message.toString())))
                }
        }

    }

}