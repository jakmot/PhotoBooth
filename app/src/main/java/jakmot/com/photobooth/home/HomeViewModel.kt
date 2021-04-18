package jakmot.com.photobooth.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jakmot.com.photobooth.common.Event
import jakmot.com.photobooth.domain.PhotoData
import jakmot.com.photobooth.file.ExifTagSetter
import jakmot.com.photobooth.file.FileManager
import java.io.File
import java.time.LocalDateTime

class HomeViewModel(
    private val exifTagSetter: ExifTagSetter,
    private val photoFileManager: FileManager,
) : ViewModel() {

    private var currentPhoto: PhotoData? = null

    private val tempFileCreatedEvent = MutableLiveData<Event<File>>()

    fun getTempFileCreatedEvent(): LiveData<Event<File>> = tempFileCreatedEvent

    fun onTakePhotoClicked(){
        tempFileCreatedEvent.value = Event(createImageFile())
    }

    private fun createImageFile(): File {
        val creationDate = LocalDateTime.now()
        return photoFileManager.createTempFile().apply {
            currentPhoto = PhotoData(
                name = nameWithoutExtension,
                fullName = name,
                filePath = absolutePath,
                creationDate = creationDate,
            )
        }
    }
}