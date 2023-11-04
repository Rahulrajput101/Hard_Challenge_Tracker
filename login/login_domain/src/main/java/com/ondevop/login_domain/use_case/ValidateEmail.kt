package com.ondevop.login_domain.use_case

import android.util.Patterns
import com.ondevop.login_domain.ValidateResult
import com.ondevop.core.uitl.UiText
import com.ondevop.login_domain.EmailValidator
import javax.inject.Inject

class ValidateEmail @Inject constructor(
    private val emailValidator: EmailValidator
)  {

    operator fun invoke(email: String) : ValidateResult {

        if(email.isBlank()){
            return ValidateResult(
                successful = false,
                errorMessage = UiText.StringResource(com.ondevop.core.R.string.please_enter_all_the_fields)
            )
        }
        if(!emailValidator.isValidEmail(email)){
            return ValidateResult(
                successful = false,
                errorMessage = UiText.StringResource(com.ondevop.core.R.string.not_valid_email)
            )
        }

        return ValidateResult(
            successful = true
        )
    }
}