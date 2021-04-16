package jakmot.com.photobooth

import android.content.ActivityNotFoundException
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { wasPhotoSaved ->
            if (wasPhotoSaved) {
                Toast.makeText(this, "Photo saved", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.takePhoto).setOnClickListener { onTakePhotoClicked() }
        findViewById<Button>(R.id.seePhotos).setOnClickListener {
            Toast.makeText(this, "See photos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onTakePhotoClicked() {
        try {
            takePicture.launch(Uri.EMPTY)
        } catch (error: ActivityNotFoundException) {
            Log.e(MainActivity::class.java.name, null, error)
            NoCameraAppDialog().show(supportFragmentManager, null)
        }
    }
}