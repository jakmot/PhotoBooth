package jakmot.com.photobooth.gallery

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import jakmot.com.photobooth.R
import jakmot.com.photobooth.domain.PhotoData
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryActivity : AppCompatActivity() {

    private val galleryViewModel: GalleryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_activity)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val photoAdapter = PhotoAdapter(::onPhotoSelected)
        findViewById<RecyclerView>(R.id.recyclerView).apply {
            adapter = photoAdapter
        }
        galleryViewModel.getPhotos().observe(this) { photos ->
            photoAdapter.photoDataList = photos
        }
        galleryViewModel.onInit()
    }

    private fun onPhotoSelected(photoData: PhotoData) {
        Intent(this, PhotoActivity::class.java)
            .putExtra(PhotoActivity.FILE_PATH_EXTRA, photoData.filePath)
            .let { startActivity(it) }
    }
}