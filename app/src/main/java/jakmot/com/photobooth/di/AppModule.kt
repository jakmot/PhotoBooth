package jakmot.com.photobooth.di

import android.os.Environment
import jakmot.com.photobooth.debug.Logger
import jakmot.com.photobooth.debug.SimpleLogger
import jakmot.com.photobooth.file.*
import jakmot.com.photobooth.gallery.GalleryViewModel
import jakmot.com.photobooth.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.time.LocalDateTime

fun appModule() = module {
    single(named("fileType")) { ".jpg" }
    single(named("filePrefix")) { "IMG" }
    single<Logger> { SimpleLogger() }
    factory<() -> LocalDateTime> { { LocalDateTime.now() } }
    single(named("photoLocation")) {
        androidApplication().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    }

    single<ExifTagSetter> { ExifTagSetterImpl(get()) }
    single<ExifTagReader> { ExifTagReaderImpl(get()) }
    single(named("photoFileManager")) {
        FileManager(
            rootDirectory = get(named("photoLocation")),
            fileType = get(named("fileType")),
            filePrefix = get(named("filePrefix")),
        )
    }

    viewModel { HomeViewModel(get(), get(named("photoFileManager")), get(), get()) }
    viewModel { GalleryViewModel(get(), get(named("photoFileManager"))) }
}