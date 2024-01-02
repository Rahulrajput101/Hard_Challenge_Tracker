package com.ondevop.login_presentation.sign_up

import com.ondevop.core_domain.uitl.UiText


data class SignUpState(
    val name: String ="",
    val profileUri: String? ="",
    val email: String ="",
    val emailError: UiText? = null,
    val password: String ="",
    val passwordError: UiText? =null,
    val repeatedPassword: String ="",
    val repeatedPasswordError: UiText? = null,
    val isLoading: Boolean = false,
)
