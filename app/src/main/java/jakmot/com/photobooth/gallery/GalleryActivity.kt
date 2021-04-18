package jakmot.com.photobooth.gallery

import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import jakmot.com.photobooth.R
import jakmot.com.photobooth.domain.PhotoData
import jakmot.com.photobooth.file.ExifTagReader
import java.time.LocalDateTime

class GalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_activity)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val photoDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val photoList = photoDirectory?.listFiles()?.map { file ->
            val creationDate = ExifTagReader().readDateTime(file) ?: LocalDateTime.MIN
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