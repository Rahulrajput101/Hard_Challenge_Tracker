package com.ondevop.login_domain.model

import android.net.Uri

data class UserInfo(
    val userName : String,
    val profileUri: Uri?,
)
