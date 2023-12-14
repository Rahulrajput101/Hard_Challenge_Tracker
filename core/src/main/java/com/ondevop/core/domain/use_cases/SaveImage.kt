package com.ondevop.core.domain.use_cases

import android.net.Uri
import com.ondevop.core.domain.repository.SaveImageRepository
import javax.inject.Inject

class SaveImage @Inject constructor(
    private val repository: SaveImageRepository
) {
    suspend operator fun invoke(uri: Uri?) : Result<String> {
       return repository.savePhotoToInternalStorage(uri)
    }
}