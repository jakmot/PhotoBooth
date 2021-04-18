package jakmot.com.photobooth.di

import android.os.Environment
import jakmot.com.photobooth.file.ExifTagReader
import jakmot.com.photobooth.file.ExifTagSetter
import jakmot.com.photobooth.file.FileManager
import jakmot.com.photobooth.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun appModule() = module {
    single { ExifTagSetter() }
    single { ExifTagReader() }
    single(named("photoLocation")) {
        androidApplication().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    }
    single(named("fileType")) { ".jpg" }
    single(named("filePrefix")) { "IMG" }
    single(named("photoFileManager")) {
        FileManager(
            rootDirectory = get(named("photoLocation")),
            fileType = get(named("fileType")),
            filePrefix = get(named("filePrefix")),
        )
    }
    viewModel { HomeViewModel(get(), get(named("photoFileManager"))) }
}