package jakmot.com.photobooth

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val creationTime = itemView.findViewById<TextView>(R.id.creationTime)

    fun bind(photoData: PhotoData) {
        creationTime.text = photoData.creationTime
    }
}
