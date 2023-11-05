package com.ondevop.login_presentation.sign_up


sealed class SignUpEvent{
    object OnCreateClick: SignUpEvent()
    data class UpdateName(val name: String) : SignUpEvent()
    data class UpdateProfileUir(val profileUri: String?) : SignUpEvent()
    data class UpdateEmail(val email: String) : SignUpEvent()
    data class UpdatePassword(val password: String) : SignUpEvent()
    data class UpdateRepeatedPassword(val repeatedPassword: String) : SignUpEvent()
}
