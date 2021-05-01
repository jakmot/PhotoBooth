package jakmot.com.photobooth.home

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import jakmot.com.photobooth.R
import jakmot.com.photobooth.databinding.EnterPhotoNameDialogBinding


class EnterPhotoNameDialog : DialogFragment() {
    private var defaultName: String? = null

    var onNameEnteredListener: (String) -> Unit = { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            defaultName = bundle.getString(DEFAULT_NAME_ARG)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let { activity ->
            val binding = EnterPhotoNameDialogBinding.inflate(activity.layoutInflater)
            binding.fileName.setText(defaultName)
            AlertDialog.Builder(activity)
                .setMessage(getString(R.string.enter_photo_name_dialog_message))
                .setView(binding.root)
                .setPositiveButton(
                    getString(R.string.enter_photo_name_dialog_positive_button)
                ) { _, _ ->
                    onNameEnteredListener(binding.fileName.text.toString())
                }
                .create().apply {
                    setCanceledOnTouchOutside(false)
                }
        }
    }

    companion object {
        const val DEFAULT_NAME_ARG = "DEFAULT_NAME_ARG"
    }
}