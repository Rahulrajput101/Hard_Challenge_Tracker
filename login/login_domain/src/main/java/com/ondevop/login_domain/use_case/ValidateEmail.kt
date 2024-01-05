package com.ondevop.login_domain.use_case

import android.util.Patterns
import com.ondevop.core_domain.uitl.UiText
import com.ondevop.login_domain.ValidateResult
import com.ondevop.login_domain.EmailValidator
import com.ondevop.core_domain.R
import javax.inject.Inject

class ValidateEmail @Inject constructor(
    private val emailValidator: EmailValidator
)  {

    operator fun invoke(email: String) : ValidateResult {

        if(email.isBlank()){
            return ValidateResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.please_enter_all_the_fields)
            )
        }
        if(!emailValidator.isValidEmail(email)){
            return ValidateResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.not_valid_email)
            )
        }

        return ValidateResult(
            successful = true
        )
    }
}