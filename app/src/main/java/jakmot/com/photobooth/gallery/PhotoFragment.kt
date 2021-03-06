package jakmot.com.photobooth.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import jakmot.com.photobooth.R
import jakmot.com.photobooth.databinding.PhotoFragmentBinding

class PhotoFragment : Fragment() {

    private var binding: PhotoFragmentBinding? = null

    private val args: PhotoFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_image_enter)
        sharedElementReturnTransition = null
    }

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
        postponeEnterTransition()
        with(requireBinding().photo) {
            transitionName = args.photoPath
            Glide.with(this)
                .load(args.photoPath)
                .into(this)

            doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    private fun requireBinding(): PhotoFragmentBinding =
        binding
            ?: throw IllegalStateException("Accessing binding outside of Fragment lifecycle: PhotoFragment")


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}