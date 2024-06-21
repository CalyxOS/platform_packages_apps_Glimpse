package org.lineageos.glimpse.fragments

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.preference.PreferenceManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.lineageos.glimpse.R
import org.lineageos.glimpse.ext.createDeleteRequest
import org.lineageos.glimpse.ext.createTrashRequest
import org.lineageos.glimpse.ext.permanentlyDeleteFiles

class DeleteDialogFragment private constructor(): DialogFragment() {

    companion object {
        private const val TAG = "DeleteDialogFragment"
        private const val ARG_URI = "URI"

        fun show(uri: Uri, fragmentManager: FragmentManager) {
            DeleteDialogFragment().apply {
                arguments = bundleOf(ARG_URI to uri.toString())
                show(fragmentManager, TAG)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.dialog_delete, null)
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_confirmation_title)
            .setMessage(R.string.delete_confirmation_message)
            .setView(view)
            .setPositiveButton(getString(android.R.string.ok)) { _, _ -> delete() }
            .setNegativeButton(getString(android.R.string.cancel)) { _, _ -> dialog?.dismiss() }
            .create()
    }

    private fun delete() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        if (sharedPreferences.permanentlyDeleteFiles) {
            requireContext().contentResolver.createDeleteRequest(
                Uri.parse(requireArguments().getString(ARG_URI))
            )
        } else {
            requireContext().contentResolver.createTrashRequest(
                false,
                Uri.parse(requireArguments().getString(ARG_URI))
            )
        }
    }
}
