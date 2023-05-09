package com.example.foodhub.database

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.File
import java.io.FileInputStream

class ImageStorageManager {
    companion object {

        fun saveToInternalStorage(context: Context, bitmapImage: Bitmap, imageFileName: String): String {
            context.openFileOutput(imageFileName, Context.MODE_PRIVATE).use { fos ->
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 25, fos)
            }
            return context.filesDir.absolutePath + "/" + imageFileName
        }

        fun getImageFromInternalStorage(context: Context, imageFileName: String): Bitmap? {
            val directory = context.filesDir
            val file = File(directory, imageFileName)

            return if (file.exists()) {
                Log.d("ImageStorageManager", "$file exists.")
                BitmapFactory.decodeStream(FileInputStream(file))
            } else {
                Log.d("ImageStorageManager", "$file doesn't exist, creating $directory")
                directory.mkdir()        // Create directory if it doesn't exist
                null
            }
        }

        fun deleteImageFromInternalStorage(context: Context, imageFileName: String): Boolean {
            val directory = context.filesDir
            val file = File(directory, imageFileName)
            return file.delete()
        }

        fun deleteAllImagesFromInternalStorage(context: Context, imageFileNames: List<String>): Boolean {
            val directory = context.filesDir
            var count = 0
            for (imageFileName in imageFileNames) {
                val file = File(directory, imageFileName)
                val success = file.delete()
                if (success) {
                    count++
                }
            }
            return count == imageFileNames.size
        }
    }
}