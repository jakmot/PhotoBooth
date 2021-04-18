package jakmot.com.photobooth.gallery

import jakmot.com.photobooth.InstantExecutorExtension
import jakmot.com.photobooth.di.appModule
import jakmot.com.photobooth.domain.PhotoData
import jakmot.com.photobooth.file.ExifTagReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.api.io.TempDir
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import java.io.File
import java.time.LocalDateTime


@ExtendWith(InstantExecutorExtension::class)
class GalleryViewModelTest : KoinTest {

    @TempDir
    lateinit var tempDir: File

    private var exifTagReaderFake: ExifTagReader = object : ExifTagReader {
        override fun readDateTime(file: File): LocalDateTime? = LocalDateTime.MAX
    }

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            appModule(),
            module(override = true) {
                single { exifTagReaderFake }
                single(named("photoLocation")) { tempDir }
            })
    }

    private val galleryViewModel: GalleryViewModel by inject()

    @Test
    fun `Should emit correct element On onInit`() {
        val file = File.createTempFile(
            "testFile",
            "jpg",
            tempDir
        )
        exifTagReaderFake = object : ExifTagReader {
            override fun readDateTime(file: File): LocalDateTime? = null
        }

        galleryViewModel.onInit()

        assertThat(galleryViewModel.getPhotos().value!!.first())
            .isEqualTo(
                PhotoData(
                    name = file.nameWithoutExtension,
                    fullName = file.name,
                    filePath = file.absolutePath,
                    creationDate = LocalDateTime.MIN,
                )
            )
    }

    @Test
    fun `Should emit 3 elements When 3 photos were made On onInit`() {
        repeat(3) { index ->
            File.createTempFile(
                "testFile${index}",
                "jpg",
                tempDir
            )
        }
        galleryViewModel.onInit()

        assertThat(galleryViewModel.getPhotos().value)
            .hasSize(3)
    }
}