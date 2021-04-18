package jakmot.com.photobooth.gallery

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.exifinterface.media.ExifInterface
import androidx.recyclerview.widget.RecyclerView
import jakmot.com.photobooth.R
import jakmot.com.photobooth.domain.PhotoData
import jakmot.com.photobooth.utils.EXIF_DATE_TIME_FORMAT
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_activity)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val photoDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val photoList = photoDirectory?.listFiles()?.map { file ->
            val creationDate = try {
                val exif = ExifInterface(file)
                exif.getAttribute(ExifInterface.TAG_DATETIME)?.let { dateTime ->
                    LocalDateTime.parse(
                        dateTime,
                        DateTimeFormatter.ofPattern(EXIF_DATE_TIME_FORMAT)
                    )
                } ?: LocalDateTime.MIN
            } catch (exception: IOException) {
                Log.e(this::class.java.name, null, exception)
                LocalDateTime.MIN
            }
            PhotoData(
                name = file.nameWithoutExtension,
                fullName = file.name,
                filePath = file.absolutePath,
                creationDate = creationDate,
            )
        }.orEmpty()

        recyclerView.adapter = PhotoAdapter(photoList)
    }
}