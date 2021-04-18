package jakmot.com.photobooth.di

import jakmot.com.photobooth.file.ExifTagReader
import jakmot.com.photobooth.file.ExifTagSetter
import org.koin.dsl.module

fun appModule() =  module {
    single { ExifTagSetter() }
    single { ExifTagReader() }
}