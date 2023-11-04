package com.ondevop.login_domain.use_case

import android.util.Patterns
import com.ondevop.login_domain.ValidateResult
import com.ondevop.core.uitl.UiText
import com.ondevop.login_domain.EmailValidator
import javax.inject.Inject

class ValidatePassword @Inject constructor(

)  {

    operator fun invoke(password: String) : ValidateResult {

        if(password.length < 8){
            return ValidateResult(
                successful = false,
                errorMessage = UiText.StringResource(com.ondevop.core.R.string.password_needs_to_contain_8_characters)
            )
        }
        val passwordContainsDigitsAndLetters = password.any { it.isLetterOrDigit() }
        if(!passwordContainsDigitsAndLetters){
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