package jakmot.com.photobooth.file

import java.io.File

class FileManager(
    private val rootDirectory: File,
    private val fileType: String,
    private val filePrefix: String,
) {

    fun createTempFile(): File {
        return File.createTempFile(
            filePrefix,
            fileType,
            rootDirectory,
        )
    }
}