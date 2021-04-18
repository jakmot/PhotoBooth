package jakmot.com.photobooth.file

import android.util.Log
import androidx.exifinterface.media.ExifInterface
import jakmot.com.photobooth.home.HomeActivity
import java.io.File
import java.io.IOException
import java.time.LocalDateTime

class ExifTagSetter {

    fun addDateTime(filePath: String, creationDate: LocalDateTime) {
        try {
            ExifInterface(File(filePath)).apply {
                setAttribute(
                    ExifInterface.TAG_DATETIME,
                    EXIF_DATE_TIME_FORMATTER.format(creationDate)
                )
                saveAttributes()
            }
        } catch (exception: IOException) {
            Log.e(HomeActivity::class.java.name, null, exception)
        }
    }
}