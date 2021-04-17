package jakmot.com.photobooth.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import jakmot.com.photobooth.R
import jakmot.com.photobooth.gallery.GalleryActivity
import jakmot.com.photobooth.home.ErrorDialog.Companion.MESSAGE_ARG
import java.io.File
import java.io.IOException
import java.time.Instant

class HomeActivity : AppCompatActivity(), EnterPhotoNameDialog.OnNameEntered {

    private var currentFileName: String? = null

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { wasPhotoSaved ->
            if (wasPhotoSaved) {
                EnterPhotoNameDialog()
                    .apply {
                        arguments = bundleOf(
                            EnterPhotoNameDialog.DEFAULT_NAME_ARG to currentFileName
                        )
                    }
                    .show(supportFragmentManager, null)
            } else {
                Toast.makeText(this, "Operation canceled", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        findViewById<Button>(R.id.takePhoto).setOnClickListener { onTakePhotoClicked() }
        findViewById<Button>(R.id.seePhotos).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    GalleryActivity::class.java
                )
            )
        }
    }

    private fun onTakePhotoClicked() {
        try {
            val imageFile = createImageFile()
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.jakmot.fileprovider",
                imageFile
            )
            takePicture.launch(photoURI)
        } catch (error: ActivityNotFoundException) {
            Log.e(HomeActivity::class.java.name, null, error)
            ErrorDialog().apply {
                arguments = bundleOf(
                    MESSAGE_ARG to "No camera app"
                )
            }.show(supportFragmentManager, null)
        } catch (error: IOException) {
            Log.e(HomeActivity::class.java.name, null, error)

            ErrorDialog().show(supportFragmentManager, null)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = Instant.now().toString()
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            timeStamp,
            ".jpg",
            storageDir
        ).apply {
            currentFileName = timeStamp
        }
    }

    override fun invoke(text: String) {
        Toast.makeText(this, "File name: $text", Toast.LENGTH_SHORT).show()
    }
}