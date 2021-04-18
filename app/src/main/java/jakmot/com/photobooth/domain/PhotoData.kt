package jakmot.com.photobooth.domain

import java.time.LocalDateTime

data class PhotoData(
    val name: String,
    val fullName: String,
    val filePath: String,
    val creationDate: LocalDateTime,
)