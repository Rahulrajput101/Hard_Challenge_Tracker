package com.ondevop.login_presentation.sign_up

import com.ondevop.core.uitl.UiText

data class SignUpState(
    val email: String ="",
    val emailError: UiText? = null,
    val password: String ="",
    val passwordError: UiText? =null,
    val repeatedPassword: String ="",
    val repeatedPasswordError: UiText? = null
)
