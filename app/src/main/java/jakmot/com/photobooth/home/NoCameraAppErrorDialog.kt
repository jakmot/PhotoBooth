package jakmot.com.photobooth.home

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import jakmot.com.photobooth.R

class NoCameraAppErrorDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let { activity ->
            AlertDialog.Builder(activity)
                .setMessage(getString(R.string.no_camera_app_error_dialog_message))
                .setPositiveButton(
                    getString(R.string.no_camera_app_error_dialog_positive_button)
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