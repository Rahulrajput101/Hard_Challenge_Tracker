package com.ondevop.core_domain.uitl

import android.Manifest
import android.annotation.SuppressLint

enum class Permission {
    POST_NOTIFICATIONS;
    @SuppressLint("InlinedApi")
    fun toPermissionString(): String {
        return when (this) {
            POST_NOTIFICATIONS -> Manifest.permission.POST_NOTIFICATIONS
        }
    }
}