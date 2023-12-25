package com.ondevop.core.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.ondevop.core.domain.repository.SaveImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class SaveImageRepositoryImp(
   private val context: Context
) : SaveImageRepository {
    override suspend fun savePhotoToInternalStorage(uri: Uri?): Result<String> {
        val kermiBytes = uri?.let {
            try {
                context.contentResolver.openInputStream(it)?.use {
                    it.readBytes()
                }
            } catch (e: Exception) {
                return Result.failure(Exception("Error reading InputStream: $e"))
            }
        } ?: return Result.failure(Exception("Null URI"))

        // Specify the destination path in internal storage
        val destinationFileName = "photo_${System.currentTimeMillis()}.jpg"
        val destinationPath = context.filesDir.absolutePath + File.separator + destinationFileName

        return try {
            withContext(Dispatchers.IO) {
                FileOutputStream(destinationPath).use {
                    it.write(kermiBytes)
                }
            }
            val savedUri = Uri.fromFile(File(destinationPath))
            Result.success(savedUri.toString())
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}