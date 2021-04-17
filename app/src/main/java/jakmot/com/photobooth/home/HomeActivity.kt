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
import jakmot.com.photobooth.domain.PhotoData
import jakmot.com.photobooth.gallery.GalleryActivity
import jakmot.com.photobooth.home.ErrorDialog.Companion.MESSAGE_ARG
import java.io.File
import java.io.IOException
import java.time.Instant

class HomeActivity : AppCompatActivity(), EnterPhotoNameDialog.OnNameEnteredListener {

    private var currentPhoto: PhotoData? = null

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { wasPhotoSaved ->
            if (wasPhotoSaved) {
                EnterPhotoNameDialog()
                    .apply {
                        arguments = bundleOf(
                            EnterPhotoNameDialog.DEFAULT_NAME_ARG to currentPhoto?.name
                        )
                    }
                    .show(supportFragmentManager, "enter_phone_number_dialog")
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
            }.show(supportFragmentManager, "error_dialog")
        } catch (error: IOException) {
            Log.e(HomeActivity::class.java.name, null, error)

            ErrorDialog().show(supportFragmentManager, "error_dialog")
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val name = Instant.now().toString()
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            name,
            ".jpg",
            storageDir
        ).apply {
            currentPhoto = PhotoData(
                name = name,
                filePath = absolutePath,
            )
        }
    }

    override fun onNameEntered(newName: String) {
        currentPhoto?.let { (name, filePath) ->
            if (name != newName) {
                val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                File(filePath).renameTo(
                    File.createTempFile(
                        newName,
                        ".jpg",
                        storageDir
                    )
                )
            }
        }
        currentPhoto = null
    }
}