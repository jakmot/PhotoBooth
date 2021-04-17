package jakmot.com.photobooth.home

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ErrorDialog : DialogFragment() {
    private var message: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            message = bundle.getString(MESSAGE_ARG)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let { activity ->
            AlertDialog.Builder(activity)
                .setMessage(message ?: "Something went wrong")
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

    companion object {
        const val MESSAGE_ARG = "MESSAGE_ARG"
    }
}