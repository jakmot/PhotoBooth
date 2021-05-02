package jakmot.com.photobooth.gallery

import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jakmot.com.photobooth.R
import jakmot.com.photobooth.common.getDimInPx
import jakmot.com.photobooth.databinding.PhotoItemBinding
import jakmot.com.photobooth.domain.PhotoData
import java.time.LocalDateTime
import kotlin.math.max
import kotlin.math.min

class PhotoViewHolder(
    private val binding: PhotoItemBinding,
    private val onPhotoSelected: (PhotoData, ImageView) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private val resources = itemView.resources

    fun bind(photoData: PhotoData) {
        val (_, fullName, filePath, creationDate) = photoData
        with(binding) {
            nameLabel.text = fullName
            createdLabel.text = prepareCreationDate(creationDate)
            Glide.with(binding.root.context)
                .load(filePath)
                .thumbnail(calculateScale(filePath))
                .into(thumbnail)
            thumbnail.transitionName = filePath
            photoItem.setOnClickListener { onPhotoSelected(photoData, thumbnail) }
        }
    }

    private fun prepareCreationDate(creationDate: LocalDateTime): String? =
        if (creationDate != LocalDateTime.MIN) {
            creationDate.toString()
        } else {
            null
        }

    private fun calculateScale(filePath: String): Float {
        val targetWidth = resources.getDimInPx(R.dimen.thumbnail_width)
        val targetHeight = resources.getDimInPx(R.dimen.thumbnail_height)

        return with(BitmapFactory.Options()) {
            inJustDecodeBounds = true

            BitmapFactory.decodeFile(filePath, this)

            val photoWidth = outWidth
            val photoHeight = outHeight

            val widthRatio = photoWidth / targetWidth
            val heightRatio = photoHeight / targetHeight

            val scale = max(1, min(widthRatio, heightRatio)).toFloat()
            1 / scale
        }
    }
}
