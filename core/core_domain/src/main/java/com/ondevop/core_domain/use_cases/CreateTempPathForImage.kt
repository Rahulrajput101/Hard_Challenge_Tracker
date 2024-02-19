package com.ondevop.core_domain.use_cases

import android.net.Uri
import com.ondevop.core_domain.repository.SaveImageRepository
import javax.inject.Inject

class CreateTempPathForImage @Inject constructor(
    private val repository: SaveImageRepository
) {
    suspend operator fun invoke() : String?{
        return repository.createTempImageUri()
    }

}