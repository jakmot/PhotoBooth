package jakmot.com.photobooth.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import jakmot.com.photobooth.R
import jakmot.com.photobooth.common.observeEvent
import jakmot.com.photobooth.domain.PhotoData
import jakmot.com.photobooth.file.ExifTagSetter
import jakmot.com.photobooth.file.FileManager
import jakmot.com.photobooth.gallery.GalleryActivity
import jakmot.com.photobooth.home.ErrorDialog.Companion.MESSAGE_ARG
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class HomeActivity : AppCompatActivity(), EnterPhotoNameDialog.OnNameEnteredListener {

    private val exifTagSetter: ExifTagSetter by inject()
    private val photoFileManager: FileManager by inject(named("photoFileManager"))

    private val homeViewModel: HomeViewModel by viewModel()

    private var currentPhoto: PhotoData? = null

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { wasPhotoSaved ->
            if (wasPhotoSaved) {
                addCreationDate()
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

        findViewById<Button>(R.id.takePhoto).setOnClickListener { homeViewModel.onTakePhotoClicked() }
        findViewById<Button>(R.id.seePhotos).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    GalleryActivity::class.java
                )
            )
        }
        homeViewModel.getTempFileCreatedEvent().observeEvent(this) { imageFile ->
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.jakmot.fileprovider",
                imageFile
            )
            takePicture(photoURI)
        }
    }

    private fun takePicture(photoURI: Uri) {
        try {
            takePicture.launch(photoURI)
        } catch (error: ActivityNotFoundException) {
            Log.e(HomeActivity::class.java.name, null, error)
            ErrorDialog().apply {
                arguments = bundleOf(
                    MESSAGE_ARG to "No camera app"
                )
            }.show(supportFragmentManager, "error_dialog")
        }
    }

    private fun addCreationDate() {
        currentPhoto?.let { (_, _, filePath, creationDate) ->
            exifTagSetter.addDateTime(filePath, creationDate)
        }
    }

    override fun onNameEntered(newName: String) {
        currentPhoto?.let { currentPhoto ->
            photoFileManager.renameFile(currentPhoto.filePath, newName)
        }
        currentPhoto = null
    }
}