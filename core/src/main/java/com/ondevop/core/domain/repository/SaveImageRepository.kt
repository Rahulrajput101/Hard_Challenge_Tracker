package com.ondevop.core.domain.repository

import android.net.Uri

interface SaveImageRepository {
    suspend fun savePhotoToInternalStorage(uri : Uri?) : Result<String>
}