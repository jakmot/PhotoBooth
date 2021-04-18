package jakmot.com.photobooth.file

import jakmot.com.photobooth.di.appModule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.api.io.TempDir
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import java.io.File

class FileManagerTest : KoinTest {

    @TempDir
    lateinit var tempDir: File

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            appModule(),
            module(override = true) {
                single(named("photoLocation")) { tempDir }
            })
    }

    private val fileManager: FileManager by inject(named("photoFileManager"))

    @Test
    fun `Should create temp file On createTempFile`() {
        val actualTempFile = fileManager.createTempFile()

        assertThat(actualTempFile)
            .isEqualTo(tempDir.listFiles().orEmpty().first())
        assertThat(actualTempFile.name).startsWith("IMG")
        assertThat(actualTempFile.extension).isEqualTo("jpg")
    }

    @Test
    fun `Should rename file On renameFile`() {
        val fileToBeRenamed = fileManager.createTempFile()
        fileManager.renameFile(fileToBeRenamed.absolutePath, "newName")

        assertThat(tempDir.listFiles().orEmpty().firstOrNull { it.name == "newName.jpg" })
            .isNotNull()
    }

    @Test
    fun `Should delete file On deleteFile`() {
        val fileToBeDeleted = fileManager.createTempFile()
        fileManager.deleteFile(fileToBeDeleted.absolutePath)

        assertThat(tempDir.listFiles().orEmpty())
            .isEmpty()
    }

    @Test
    fun `Should return list with 3 elements file On listAllFiles`() {
        repeat(3) {
            fileManager.createTempFile()
        }
        assertThat(fileManager.listAllFiles()).hasSize(3)
    }
}
