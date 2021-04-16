package jakmot.com.photobooth

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class NoCameraAppDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let { activity ->
            AlertDialog.Builder(activity)
                .setMessage("No camera application found")
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