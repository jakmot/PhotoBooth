package jakmot.com.photobooth.home

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


class EnterPhotoNameDialog : DialogFragment() {
    private var defaultName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            defaultName = bundle.getString(DEFAULT_NAME_ARG)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let { activity ->
            val editText = EditText(activity)
            AlertDialog.Builder(activity)
                .setMessage("Enter name")
                .setView(editText)
                .setPositiveButton(
                    "Save"
                ) { _, _ ->

                }
                .create()
        }
    }

    companion object {
        const val DEFAULT_NAME_ARG = "DEFAULT_NAME_ARG"
    }
}