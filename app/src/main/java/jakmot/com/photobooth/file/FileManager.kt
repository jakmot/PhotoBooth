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

    fun renameFile(filePath: String, newName: String) {
        val file = File(filePath)
        if (file.nameWithoutExtension != newName) {
            val newFile = File(
                rootDirectory,
                "$newName$fileType",
            )
            File(filePath).renameTo(newFile)
        }
    }

    fun listAllFiles(): Array<out File> = rootDirectory.listFiles().orEmpty()
}