package jakmot.com.photobooth.gallery

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jakmot.com.photobooth.R

class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val creationTime = itemView.findViewById<TextView>(R.id.creationTime)

    fun bind(photoData: PhotoData) {
        creationTime.text = photoData.name
    }
}
