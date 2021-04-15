package jakmot.com.photobooth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.takePhoto).setOnClickListener {
            Toast.makeText(this, "Take a photo", Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.seePhotos).setOnClickListener {
            Toast.makeText(this, "See photos", Toast.LENGTH_SHORT).show()
        }
    }
}