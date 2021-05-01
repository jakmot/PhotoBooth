package jakmot.com.photobooth.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.snackbar.Snackbar
import jakmot.com.photobooth.R
import jakmot.com.photobooth.common.observeEvent
import jakmot.com.photobooth.common.withArguments
import jakmot.com.photobooth.databinding.HomeActivityBinding
import jakmot.com.photobooth.gallery.GalleryActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class HomeActivity : AppCompatActivity(), EnterPhotoNameDialog.OnNameEnteredListener {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var binding: HomeActivityBinding

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { wasPhotoSaved ->
            if (wasPhotoSaved) {
                homeViewModel.onPhotoTaken()
            } else {
                homeViewModel.onPhotoCanceled()
                showFailedToTakePhotoMessage()
            }
        }

    private fun showFailedToTakePhotoMessage() {
        Snackbar.make(
            binding.root,
            getString(R.string.photo_not_taken_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews(binding)
        observeViewModel()
    }

    private fun setupViews(binding: HomeActivityBinding) {
        binding.takePhoto.setOnClickListener { homeViewModel.onTakePhotoClicked() }
        binding.seePhotos.setOnClickListener {
            startActivity(
                Intent(this, GalleryActivity::class.java)
            )
        }
    }

    private fun observeViewModel() {
        homeViewModel.getTempFileCreatedEvent().observeEvent(this) { imageFile ->
            takePicture(imageFile)
        }

        homeViewModel.getPhotoDefaultName().observeEvent(this) { defaultName ->
            displayEnterPhoneNameDialog(defaultName)
        }
    }

    private fun takePicture(imageFile: File) {
        val photoURI: Uri = FileProvider.getUriForFile(
            this,
            getString(R.string.file_provider_authority),
            imageFile
        )
        try {
            takePicture.launch(photoURI)
        } catch (error: ActivityNotFoundException) {
            homeViewModel.onFailToTakeAPhoto(error)
            showErrorDialog()
        }
    }

    private fun showErrorDialog() {
        NoCameraAppErrorDialog().show(supportFragmentManager, "error_dialog")
    }

    private fun displayEnterPhoneNameDialog(defaultName: String?) {
        EnterPhotoNameDialog()
            .withArguments(
                EnterPhotoNameDialog.DEFAULT_NAME_ARG to defaultName
            )
            .show(supportFragmentManager, "enter_phone_number_dialog")
    }

    override fun onNameEntered(newName: String) {
        homeViewModel.onNameEntered(newName)
        showPhotoTakenMessage()
    }
    private fun showPhotoTakenMessage() {
        Snackbar.make(
            binding.root,
            getString(R.string.photo_taken_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}