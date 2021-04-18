package jakmot.com.photobooth.home

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class NoCameraAppErrorDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let { activity ->
            AlertDialog.Builder(activity)
                .setMessage("There is no application that can handle taking photos")
                .setPositiveButton(
                    "Close app"
                ) { _, _ ->
                    activity.finish()
                }
                .create()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        requireActivity().finish()
    }
}