package jakmot.com.photobooth.gallery

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jakmot.com.photobooth.common.layoutInflater
import jakmot.com.photobooth.databinding.PhotoItemBinding
import jakmot.com.photobooth.domain.PhotoData

class PhotoAdapter(
    private val onPhotoSelected: (PhotoData) -> Unit,
) : RecyclerView.Adapter<PhotoViewHolder>() {

    var photoDataList: List<PhotoData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = PhotoItemBinding.inflate(parent.layoutInflater())
        return PhotoViewHolder(binding, onPhotoSelected)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photoDataList[position])
    }

    override fun getItemCount(): Int = photoDataList.size
}