package jakmot.com.photobooth.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import jakmot.com.photobooth.R
import jakmot.com.photobooth.home.EnterPhotoNameDialog.OnNameEntered


class EnterPhotoNameDialog : DialogFragment() {
    private var defaultName: String? = null
    private var onNameEntered: OnNameEntered = OnNameEntered { }

    fun interface OnNameEntered {
        operator fun invoke(text: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNameEntered) {
            onNameEntered = context
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            defaultName = bundle.getString(DEFAULT_NAME_ARG)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let { activity ->
            val layoutInflater = activity.layoutInflater
            val dialogBody = layoutInflater.inflate(R.layout.enter_photo_name_dialog, null)
            val fileNameInput = dialogBody.findViewById<EditText>(R.id.fileName)
            fileNameInput.setText(defaultName)
            AlertDialog.Builder(activity)
                .setMessage("Enter name")
                .setView(dialogBody)
                .setPositiveButton(
                    "Save"
                ) { _, _ ->
                    onNameEntered(fileNameInput.text.toString())
                }
                .create()
        }
    }

    companion object {
        const val DEFAULT_NAME_ARG = "DEFAULT_NAME_ARG"
    }
}