package jakmot.com.photobooth.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        binding!!.recyclerView.apply {
            adapter = photoAdapter
        }
        galleryViewModel.getPhotos().observe(this.viewLifecycleOwner) { photos ->
            photoAdapter.photoDataList = photos
        }
        galleryViewModel.onInit()
    }

    private fun onPhotoSelected(photoData: PhotoData) {
        Intent(this.activity, PhotoActivity::class.java)
            .putExtra(PhotoActivity.FILE_PATH_EXTRA, photoData.filePath)
            .let { startActivity(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}