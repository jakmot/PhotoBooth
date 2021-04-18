package jakmot.com.photobooth.gallery

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jakmot.com.photobooth.databinding.PhotoActivityBinding

class PhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = PhotoActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val filePath = intent.extras?.getString(FILE_PATH_EXTRA)

        binding.photo.setImageBitmap(
            BitmapFactory.decodeFile(filePath)
        )
    }

    companion object {
        const val FILE_PATH_EXTRA = "FILE_PATH_EXTRA"
    }
}