package com.ondevop.login_presentation.sign_in

import com.ondevop.core.uitl.UiText

data class SignInState(
    val email: String ="",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null
)
