package jakmot.com.photobooth.home

import jakmot.com.photobooth.InstantExecutorExtension
import jakmot.com.photobooth.di.appModule
import jakmot.com.photobooth.file.ExifTagSetter
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
class HomeViewModelTest : KoinTest {

    @TempDir
    lateinit var tempDir: File

    private var exifTagSetter: ExifTagSetter = object : ExifTagSetter {
        override fun addDateTime(filePath: String, creationDate: LocalDateTime) {

        }
    }

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            appModule(),
            module(override = true) {
                single { exifTagSetter }
                single(named("photoLocation")) { tempDir }
                factory<() -> LocalDateTime> { { LocalDateTime.MIN } }
            })
    }

    private val homeViewModel: HomeViewModel by inject()

    @Test
    fun `Should emit temp file On onTakePhotoClicked`() {
        homeViewModel.onTakePhotoClicked()

        val actualFile = homeViewModel.getTempFileCreatedEvent().value!!.content
        assertThat(actualFile!!.name).startsWith("IMG")
        assertThat(actualFile).exists()
    }

    @Test
    fun `Should emit default name onPhotoTaken`() {
        homeViewModel.onTakePhotoClicked()
        homeViewModel.onPhotoTaken()

        val actualDefaultName = homeViewModel.getPhotoDefaultName().value!!.content
        assertThat(actualDefaultName).startsWith("IMG")
    }

    @Test
    fun `Should remove temp file onPhotoCanceled`() {
        homeViewModel.onTakePhotoClicked()
        homeViewModel.onPhotoCanceled()

        assertThat(tempDir.list()).isEmpty()
    }

    @Test
    fun `Should rename file onNameEntered`() {
        homeViewModel.onTakePhotoClicked()
        homeViewModel.onPhotoTaken()
        val expectedName = "BEST_IMAGE_EVER"
        homeViewModel.onNameEntered(expectedName)

        val renamedFile = tempDir.listFiles().orEmpty().find { it.nameWithoutExtension == expectedName }
        assertThat(renamedFile).exists()

        val previousFile = tempDir.listFiles().orEmpty().find { it.nameWithoutExtension.startsWith("IMG") }
        assertThat(previousFile).isNull()
    }

    @Test
    fun `Should remove temp file onFailToTakeAPhoto`() {
        homeViewModel.onTakePhotoClicked()
        homeViewModel.onFailToTakeAPhoto(RuntimeException())

        assertThat(tempDir.list()).isEmpty()
    }
}