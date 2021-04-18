package jakmot.com.photobooth.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

class Event<T>(private val _content: T) {
    private var handled = false
    val content: T?
        get() = when (handled) {
            true -> null
            false -> {
                handled = true

                _content
            }
        }
}

inline fun <T> LiveData<Event<T>>.observeEvent(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    observe(owner, { event -> event.content?.let(observer) })
}