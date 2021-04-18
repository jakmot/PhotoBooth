package jakmot.com.photobooth.gallery

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import jakmot.com.photobooth.R
import jakmot.com.photobooth.common.getDimInPx
import jakmot.com.photobooth.databinding.PhotoItemBinding
import jakmot.com.photobooth.domain.PhotoData
import java.time.LocalDateTime
import kotlin.math.max
import kotlin.math.min

class PhotoViewHolder(
    private val binding: PhotoItemBinding,
    private val onPhotoSelected: (PhotoData) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private val resources = itemView.resources

    fun bind(photoData: PhotoData) {
        val (_, fullName, filePath, creationDate) = photoData
        with(binding) {
            nameLabel.text = fullName
            createdLabel.text = prepareCreationDate(creationDate)
            thumbnail.setImageBitmap(createThumbnail(filePath))
            photoItem.setOnClickListener { onPhotoSelected(photoData) }
        }
    }

    private fun prepareCreationDate(creationDate: LocalDateTime): String? =
        if (creationDate != LocalDateTime.MIN) {
            creationDate.toString()
        } else {
            null
        }

    private fun createThumbnail(filePath: String): Bitmap? {
        val targetWidth = resources.getDimInPx(R.dimen.thumbnail_width)
        val targetHeight = resources.getDimInPx(R.dimen.thumbnail_height)

        val bmOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true

            BitmapFactory.decodeFile(filePath, this)

            val photoWidth = outWidth
            val photoHeight = outHeight

            val widthRatio = photoWidth / targetWidth
            val heightRatio = photoHeight / targetHeight

            val scaleFactor = max(1, min(widthRatio, heightRatio))

            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }
        return BitmapFactory.decodeFile(filePath, bmOptions)
    }
}
