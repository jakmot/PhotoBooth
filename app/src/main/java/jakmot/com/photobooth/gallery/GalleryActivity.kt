package jakmot.com.photobooth.gallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import jakmot.com.photobooth.R
import java.time.Instant

class GalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_activity)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = PhotoAdapter(
            (0..10L).map { Instant.now().plusSeconds(it).toString() }
                .map(::PhotoData)
        )
    }
}