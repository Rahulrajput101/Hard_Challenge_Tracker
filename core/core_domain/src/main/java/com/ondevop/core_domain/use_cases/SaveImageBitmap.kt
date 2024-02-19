package com.ondevop.core_domain.use_cases

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.ondevop.core_domain.repository.SaveImageRepository
import javax.inject.Inject

class SaveImageBitmap @Inject constructor(
    private val repository: SaveImageRepository
) {
    suspend operator fun invoke(bitmap: Bitmap) : Result<String> {
       return repository.saveBitmapToInternalStorage(bitmap)
    }
}