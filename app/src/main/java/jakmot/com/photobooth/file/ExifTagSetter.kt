package jakmot.com.photobooth.file

import androidx.exifinterface.media.ExifInterface
import jakmot.com.photobooth.debug.Logger
import java.io.File
import java.io.IOException
import java.time.LocalDateTime

class ExifTagSetterImpl(private val logger: Logger) : ExifTagSetter {

    override fun addDateTime(filePath: String, creationDate: LocalDateTime) {
        try {
            ExifInterface(File(filePath)).apply {
                setAttribute(
                    ExifInterface.TAG_DATETIME,
                    EXIF_DATE_TIME_FORMATTER.format(creationDate)
                )
                saveAttributes()
            }
        } catch (exception: IOException) {
            logger.error(exception)
        }
    }
}

interface ExifTagSetter {
    fun addDateTime(filePath: String, creationDate: LocalDateTime)
}