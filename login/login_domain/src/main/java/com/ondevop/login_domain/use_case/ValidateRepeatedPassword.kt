package com.ondevop.login_domain.use_case

import android.util.Patterns
import com.ondevop.core_domain.uitl.UiText
import com.ondevop.login_domain.ValidateResult
import com.ondevop.login_domain.EmailValidator
import com.ondevop.core_domain.R
import javax.inject.Inject

class ValidateRepeatedPassword @Inject constructor() {

    operator fun invoke(password: String, repeatedPassword: String) : ValidateResult {

        if(password != repeatedPassword){
            return ValidateResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.password_do_not_match)
            )
        }

        return ValidateResult(
            successful = true
        )
    }
}