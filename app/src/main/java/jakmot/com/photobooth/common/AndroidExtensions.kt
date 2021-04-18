package jakmot.com.photobooth.common

import androidx.fragment.app.DialogFragment
import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.core.os.bundleOf

fun Resources.getDimInPx(@DimenRes id: Int) = getDimension(id).let { dip ->
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dip,
        this.displayMetrics,
    )
}.toInt()

fun DialogFragment.withArguments(vararg pairs: Pair<String, Any?>): DialogFragment = apply {
    arguments = bundleOf(*pairs)
}

fun ViewGroup.layoutInflater(): LayoutInflater = LayoutInflater.from(context)