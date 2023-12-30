package com.ondevop.core_domain.repository

import android.net.Uri

interface SaveImageRepository {
    suspend fun savePhotoToInternalStorage(uri : Uri?) : Result<String>
}