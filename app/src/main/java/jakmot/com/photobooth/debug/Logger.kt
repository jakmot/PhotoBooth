package jakmot.com.photobooth.debug

import jakmot.com.photobooth.BuildConfig

interface Logger {
    fun error(exception: Exception)
}

class SimpleLogger : Logger {
    override fun error(exception: Exception) {
        if (BuildConfig.DEBUG) {
            println(exception)
        }
    }
}