package com.ondevop.login_domain

import com.ondevop.core_domain.uitl.UiText


data class ValidateResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)