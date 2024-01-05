package com.ondevop.settings_domain.repository

interface SettingsRepository {

    suspend fun signOut(): Result<Unit>
}