package jakmot.com.photobooth.common

import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.DimenRes

fun Resources.getDimInPx(@DimenRes id: Int) = getDimension(id).let { dip ->
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dip,
        this.displayMetrics,
    )
}.toInt()
