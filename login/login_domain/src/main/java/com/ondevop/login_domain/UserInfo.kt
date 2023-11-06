package com.ondevop.login_domain

import android.net.Uri

data class UserInfo(
    val userName : String,
    val profileUri: Uri?,
)
