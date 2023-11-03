package com.ondevop.login_presentation.sign_in

sealed class SignInEvent {
    object SignInClick : SignInEvent()
    data class UpdateEmail(val email: String) : SignInEvent()
    data class UpdatePassword(val password: String) : SignInEvent()

}
