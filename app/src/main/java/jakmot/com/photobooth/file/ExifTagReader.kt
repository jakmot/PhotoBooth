package jakmot.com.photobooth.file

import android.util.Log
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ExifTagReader {

    fun readDateTime(file: File): LocalDateTime? {
        return try {
            val exif = ExifInterface(file)
            exif.getAttribute(ExifInterface.TAG_DATETIME)?.let { dateTime ->
                parseDateTime(dateTime)
            }
        } catch (exception: IOException) {
            Log.e(this::class.java.name, null, exception)
            null
        }
    }

    private fun parseDateTime(dateTime: String) = LocalDateTime.parse(
        dateTime,
        EXIF_DATE_TIME_FORMATTER
    )
}