package jakmot.com.photobooth.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jakmot.com.photobooth.domain.PhotoData
import jakmot.com.photobooth.file.ExifTagReader
import jakmot.com.photobooth.file.FileManager
import java.time.LocalDateTime

class GalleryViewModel(
    private val exifTagReader: ExifTagReader,
    private val fileManager: FileManager,
) : ViewModel() {

    private val photos = MutableLiveData<List<PhotoData>>()

    fun getPhotos(): LiveData<List<PhotoData>> = photos

    fun onInit() {
        photos.value = fileManager.listAllFiles().map { file ->
            val creationDate = exifTagReader.readDateTime(file) ?: LocalDateTime.MIN
            PhotoData(
                name = file.nameWithoutExtension,
                fullName = file.name,
                filePath = file.absolutePath,
                creationDate = creationDate,
            )
        }
    }
}