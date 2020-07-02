package com.example.civilservantapp.download_extension

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import okio.buffer
import okio.sink
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DownloadPdf internal constructor(val context : Context){
     fun downloadQ(filename: String,byte: String): Uri
    {
        val imgBytesData: ByteArray = Base64.decode(
            byte,
            Base64.DEFAULT
        )
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) MediaStore.Downloads.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            ) else MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val values = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, filename)
            put(MediaStore.Downloads.RELATIVE_PATH, "")
            put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
            put(MediaStore.Downloads.IS_PENDING, 1)

        }

        val resolver = context.contentResolver
        val uri = resolver.insert(collection, values)
        Log.d("test",uri?.path.toString())
        uri?.let {
            resolver.openOutputStream(uri)?.use { outputStream ->
                val sink = outputStream.sink().buffer()

                imgBytesData.let { sink.write(it) }
                sink.close()
            }
            values.clear()
            values.put(MediaStore.Video.Media.IS_PENDING, 0)
            resolver.update(uri, values, null, null)
        } ?: throw RuntimeException("MediaStore failed for some reason")

        return uri
    }
    fun downLoadMiddle(filename : String,byte : String) : Uri{
        val imgBytesData: ByteArray = Base64.decode(
            byte,
            Base64.DEFAULT
        )
        @Suppress("DEPRECATION") val filesDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS);
        val dir = File(filesDir.absolutePath.toString() + "/governments/")
        dir.mkdir()
        val file = File(dir, filename)
        var os: FileOutputStream? = null
        try {
            os = FileOutputStream(file)
            os.write(imgBytesData)
            Log.d("test","OK")
            Toast.makeText(context,"File Successful Save in governments folder",
                Toast.LENGTH_SHORT).show()
            os.close()
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("test","error ${e.localizedMessage}")
        }
        return Uri.parse(file.absolutePath)
    }
    fun downloadLower(filename: String,byte: String) : Uri{
        val imgBytesData: ByteArray = Base64.decode(
            byte,
            Base64.DEFAULT
        )
        @Suppress("DEPRECATION") val sdcard = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS);
        val dir = File(sdcard.absolutePath.toString() + "/government/")
        dir.mkdir()
        val file = File(dir, filename)
        var os: FileOutputStream? = null
        try {
            os = FileOutputStream(file)
            os.write(imgBytesData)
            Log.d("test", "ok")
            os.close()

        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("test", "onClick: " + e.localizedMessage)
        }
        return Uri.fromFile(file)
    }
}