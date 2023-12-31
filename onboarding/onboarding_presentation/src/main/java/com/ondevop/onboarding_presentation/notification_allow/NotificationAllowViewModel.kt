package com.ondevop.onboarding_presentation.notification_allow

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.ondevop.core_domain.uitl.Permission
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationAllowViewModel @Inject constructor(

    ): ViewModel() {

    val visiblePermissionDialogQueue = mutableStateListOf<Permission>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: Permission,
        isGranted: Boolean
    ) {
        if(!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }
}