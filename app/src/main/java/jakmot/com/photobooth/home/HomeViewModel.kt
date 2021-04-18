package jakmot.com.photobooth.home

import android.util.Log
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

    private val photoDefaultName = MutableLiveData<Event<String?>>()

    fun getTempFileCreatedEvent(): LiveData<Event<File>> = tempFileCreatedEvent

    fun getPhotoDefaultName(): LiveData<Event<String?>> = photoDefaultName

    fun onTakePhotoClicked() {
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

    fun onPhotoTaken() {
        addCreationDate()
        photoDefaultName.value = Event(currentPhoto?.name)
    }

    fun onPhotoCanceled() {
        currentPhoto?.let {
            photoFileManager.deleteFile(it.filePath)
        }
        currentPhoto = null
    }

    private fun addCreationDate() {
        currentPhoto?.let { (_, _, filePath, creationDate) ->
            exifTagSetter.addDateTime(filePath, creationDate)
        }
    }

    fun onNameEntered(newName: String) {
        currentPhoto?.let { currentPhoto ->
            photoFileManager.renameFile(currentPhoto.filePath, newName)
        }
        currentPhoto = null
    }

    fun onFailToTakeAPhoto(error: Exception) {
        Log.e(HomeActivity::class.java.name, null, error)
        onPhotoCanceled()
    }
}