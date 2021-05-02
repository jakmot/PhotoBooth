package jakmot.com.photobooth.home

import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import jakmot.com.photobooth.R
import jakmot.com.photobooth.common.observeEvent
import jakmot.com.photobooth.common.withArguments
import jakmot.com.photobooth.databinding.HomeFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModel()

    private var binding: HomeFragmentBinding? = null

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
            requireBinding().root,
            getString(R.string.photo_not_taken_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return HomeFragmentBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        with(requireBinding()) {
            takePhoto.setOnClickListener { homeViewModel.onTakePhotoClicked() }
            seePhotos.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToGalleryFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun observeViewModel() {
        homeViewModel.getTempFileCreatedEvent().observeEvent(this.viewLifecycleOwner) { imageFile ->
            takePicture(imageFile)
        }

        homeViewModel.getPhotoDefaultName().observeEvent(this.viewLifecycleOwner) { defaultName ->
            displayEnterPhoneNameDialog(defaultName)
        }
    }

    private fun takePicture(imageFile: File) {
        val photoURI: Uri = FileProvider.getUriForFile(
            requireContext(),
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
        NoCameraAppErrorDialog().show(parentFragmentManager, "error_dialog")
    }

    private fun displayEnterPhoneNameDialog(defaultName: String?) {
        EnterPhotoNameDialog()
            .apply {
                onNameEnteredListener = ::onNameEntered
            }
            .withArguments(
                EnterPhotoNameDialog.DEFAULT_NAME_ARG to defaultName
            )
            .show(parentFragmentManager, "enter_phone_number_dialog")
    }

    private fun onNameEntered(newName: String) {
        homeViewModel.onNameEntered(newName)
        showPhotoTakenMessage()
    }

    private fun showPhotoTakenMessage() {
        Snackbar.make(
            requireBinding().root,
            getString(R.string.photo_taken_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun requireBinding(): HomeFragmentBinding =
        binding
            ?: throw IllegalStateException("Accessing binding outside of Fragment lifecycle: HomeFragment")

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}