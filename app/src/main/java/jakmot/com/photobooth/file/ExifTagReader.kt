package jakmot.com.photobooth.file

import androidx.exifinterface.media.ExifInterface
import jakmot.com.photobooth.debug.Logger
import java.io.File
import java.io.IOException
import java.time.LocalDateTime

class ExifTagReaderImpl(private val logger: Logger) : ExifTagReader {

    override fun readDateTime(file: File): LocalDateTime? {
        return try {
            val exif = ExifInterface(file)
            exif.getAttribute(ExifInterface.TAG_DATETIME)?.let { dateTime ->
                parseDateTime(dateTime)
            }
        } catch (exception: IOException) {
            logger.error(exception)
            null
        }
    }

    private fun parseDateTime(dateTime: String) = LocalDateTime.parse(
        dateTime,
        EXIF_DATE_TIME_FORMATTER
    )
}

interface ExifTagReader {
    fun readDateTime(file: File): LocalDateTime?
}