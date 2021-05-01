package jakmot.com.photobooth.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jakmot.com.photobooth.databinding.PhotoFragmentBinding

class PhotoFragment : Fragment() {

    private var binding: PhotoFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return PhotoFragmentBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)

//        val filePath = .extras?.getString(FILE_PATH_EXTRA)

//        Glide.with(this)
//            .load(filePath)
//            .into(binding.photo)
    }

    companion object {
        const val FILE_PATH_EXTRA = "FILE_PATH_EXTRA"
    }
}