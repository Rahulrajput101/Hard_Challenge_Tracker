package com.ondevop.core_domain.uitl

import android.Manifest
import android.annotation.SuppressLint

enum class Permission {
    CAMERA,
    POST_NOTIFICATIONS;
    @SuppressLint("InlinedApi")
    fun toPermissionString(): String {
        return when (this) {
            CAMERA -> Manifest.permission.CAMERA
            POST_NOTIFICATIONS -> Manifest.permission.POST_NOTIFICATIONS
            // Add more cases as needed
        }
    }
}