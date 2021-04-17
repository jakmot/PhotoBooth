package jakmot.com.photobooth.gallery

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import jakmot.com.photobooth.R

class PhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_activity)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val filePath = intent.extras?.getString(FILE_PATH_EXTRA)

        findViewById<ImageView>(R.id.photo).setImageBitmap(
            BitmapFactory.decodeFile(filePath)
        )
    }

    companion object {
        const val FILE_PATH_EXTRA = "FILE_PATH_EXTRA"
    }
}