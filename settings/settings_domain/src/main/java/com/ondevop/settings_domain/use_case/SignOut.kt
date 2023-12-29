package com.ondevop.settings_domain.use_case

import com.ondevop.settings_domain.repository.SettingsRepository
import javax.inject.Inject

class SignOut @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(): Result<Unit>{
        return repository.signOut()
    }
}