package com.ondevop.core.domain.use_cases

import android.net.Uri
import android.util.Log
import com.ondevop.core.domain.repository.SaveImageRepository
import javax.inject.Inject

class SaveImage @Inject constructor(
    private val repository: SaveImageRepository
) {
    suspend operator fun invoke(uri: Uri?) : Result<String> {
        val imageUir =  repository.savePhotoToInternalStorage(uri)
        Log.d("si"," uri: $imageUir $")
       return imageUir
    }
}