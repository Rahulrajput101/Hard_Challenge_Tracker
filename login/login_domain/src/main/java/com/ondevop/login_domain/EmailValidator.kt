package com.ondevop.login_domain

interface EmailValidator {
    fun isValidEmail(email: String): Boolean
}