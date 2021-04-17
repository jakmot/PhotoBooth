package jakmot.com.photobooth.gallery

import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import jakmot.com.photobooth.R

class GalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_activity)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val photoDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val photoList = photoDirectory?.listFiles()?.map {
            PhotoData(
                name = it.name,
                filePath = it.absolutePath,
            )
        }.orEmpty()

        recyclerView.adapter = PhotoAdapter(photoList)
    }

}