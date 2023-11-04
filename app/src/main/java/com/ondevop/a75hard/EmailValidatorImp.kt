package com.ondevop.a75hard

import android.util.Patterns
import com.ondevop.login_domain.EmailValidator

class EmailValidatorImp : EmailValidator {
    override fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}