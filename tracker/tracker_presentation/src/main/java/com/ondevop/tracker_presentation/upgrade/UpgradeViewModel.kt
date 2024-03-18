package com.ondevop.tracker_presentation.upgrade

import androidx.lifecycle.ViewModel
import com.ondevop.core_domain.prefernces.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpgradeViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    suspend fun setProVersion(isProVersion: Boolean) = preferences.saveIsProVersion(isProVersion)
}