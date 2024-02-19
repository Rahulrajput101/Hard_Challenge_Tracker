package com.ondevop.core_domain.repository

import android.graphics.Bitmap
import android.net.Uri

interface SaveImageRepository {
    suspend fun savePhotoToInternalStorage(uri : Uri?) : Result<String>
    suspend fun saveBitmapToInternalStorage(bitmap : Bitmap) : Result<String>
    suspend fun createTempImageUri() : String?
}