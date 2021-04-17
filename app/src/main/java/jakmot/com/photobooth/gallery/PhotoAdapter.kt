package jakmot.com.photobooth.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jakmot.com.photobooth.R
import jakmot.com.photobooth.domain.PhotoData

class PhotoAdapter(private val photoDataList: List<PhotoData>) :
    RecyclerView.Adapter<PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.photo_item, parent, false)
            .let(::PhotoViewHolder)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photoDataList[position])
    }

    override fun getItemCount(): Int = photoDataList.size
}