package com.ondevop.login_presentation.sign_in

sealed class SignInEvent {
    data class SignInClick(val signInState: SignInState) : SignInEvent()
    object  SignUpClick : SignInEvent()
}
