package jakmot.com.photobooth.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import jakmot.com.photobooth.databinding.GalleryFragmentBinding
import jakmot.com.photobooth.domain.PhotoData
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryFragment : Fragment() {

    private val galleryViewModel: GalleryViewModel by viewModel()

    private var binding: GalleryFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return GalleryFragmentBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)

        val photoAdapter = PhotoAdapter(::onPhotoSelected)
        requireBinding().recyclerView.apply {
            adapter = photoAdapter
        }
        galleryViewModel.getPhotos().observe(this.viewLifecycleOwner) { photos ->
            photoAdapter.photoDataList = photos
        }
        galleryViewModel.onInit()
    }

    private fun onPhotoSelected(photoData: PhotoData) {
        val action = GalleryFragmentDirections.actionGalleryFragmentToPhotoFragment(photoData.filePath)

        findNavController().navigate(action)
    }

    private fun requireBinding(): GalleryFragmentBinding =
        binding
            ?: throw IllegalStateException("Accessing binding outside of Fragment lifecycle: GalleryFragment")


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}