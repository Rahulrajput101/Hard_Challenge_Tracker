package com.ondevop.core_data.repository

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.ondevop.core_domain.repository.SaveImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime

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

    override suspend fun saveBitmapToInternalStorage( bitmap: Bitmap): Result<String> {
        // Convert Bitmap to ByteArray
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val bitmapData = byteArrayOutputStream.toByteArray()

        // Specify the destination path in internal storage
        val destinationFileName = "photo_${System.currentTimeMillis()}.jpg"
        val destinationPath = context.filesDir.absolutePath + File.separator + destinationFileName

        return try {
            // Write ByteArray to FileOutputStream
            withContext(Dispatchers.IO) {
                FileOutputStream(destinationPath).use { fileOutputStream ->
                    fileOutputStream.write(bitmapData)
                }
            }

            // Return the saved file path as a string
            Result.success(destinationPath)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    override suspend fun createTempImageUri(): String? {
        return try {
          //  val imageFile = File.createTempFile("temp_images", ".jpg", context.externalCacheDir)
            val imageFile = File(context.cacheDir,"temp_images.jpg" )
            FileProvider.getUriForFile(context, "com.ondevop.a75hard.fileProvider", imageFile).toString()
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}