package com.ondevop.tracker_presentation.tracker_overview

import com.ondevop.core_domain.uitl.Permission

sealed class PermissionHandleEvent {

    object DismissDialog : PermissionHandleEvent()
    data class OnPermissionResult(val permission: Permission, val isGranted: Boolean) : PermissionHandleEvent()
}