package jakmot.com.photobooth.file

import androidx.test.ext.junit.runners.AndroidJUnit4
import instrContext
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.Month


@RunWith(AndroidJUnit4::class)
class ExifTagProcessingTest {

    @get:Rule
    val folder = TemporaryFolder()

    private val exifTagSetter: ExifTagSetter = ExifTagSetterImpl()

    private val exifTagReader: ExifTagReader = ExifTagReaderImpl()

    @Test
    fun shouldAddDateTimeTagToFile() {
        val pictureFile: File = loadPictureFromAssets()
        val creationDate = LocalDate.of(2021, Month.APRIL, 18).atStartOfDay()

        exifTagSetter.addDateTime(pictureFile.absolutePath, creationDate)
        val resultDate = exifTagReader.readDateTime(pictureFile)

        assertThat(resultDate).isEqualTo("2021-04-18T00:00")
    }

    private fun loadPictureFromAssets() =
        instrContext.assets.open("sample_picture.jpg").use { inputStream ->
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            val targetFile = File.createTempFile("sample_picture", ".jpg", folder.root)
            val outStream = FileOutputStream(targetFile)
            outStream.write(buffer)
            targetFile
        }
}