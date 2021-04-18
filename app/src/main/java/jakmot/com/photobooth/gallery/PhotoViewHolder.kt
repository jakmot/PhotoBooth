package jakmot.com.photobooth.gallery

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jakmot.com.photobooth.R
import jakmot.com.photobooth.common.getDimInPx
import jakmot.com.photobooth.domain.PhotoData
import java.time.LocalDateTime
import kotlin.math.max
import kotlin.math.min

class PhotoViewHolder(
    itemView: View,
    private val onPhotoSelected: (PhotoData) -> Unit,
) : RecyclerView.ViewHolder(itemView) {

    private val resources = itemView.resources
    private val photoItem = itemView.findViewById<View>(R.id.photo_item)
    private val nameLabel = itemView.findViewById<TextView>(R.id.nameLabel)
    private val createdLabel = itemView.findViewById<TextView>(R.id.createdLabel)
    private val thumbnail = itemView.findViewById<ImageView>(R.id.thumbnail)

    fun bind(photoData: PhotoData) {
        val (_, fullName, filePath, creationDate) = photoData
        nameLabel.text = fullName
        createdLabel.text = if (creationDate != LocalDateTime.MIN) {
            creationDate.toString()
        } else {
            null
        }
        thumbnail.setImageBitmap(createThumbnail(filePath))
        photoItem.setOnClickListener { onPhotoSelected(photoData) }
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
