/*
 * SPDX-FileCopyrightText: 2024 The Calyx Institute
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.glimpse.calyx.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.CheckBox
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.preference.PreferenceManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.lineageos.glimpse.R
import org.lineageos.glimpse.calyx.ext.permanentlyDeleteFiles
import org.lineageos.glimpse.models.MediaStoreMedia

class DeleteDialogFragment private constructor(
    private val size: Int,
    private val positiveButtonClickListener: DialogInterface.OnClickListener
) : DialogFragment() {

    companion object {
        private const val TAG = "DeleteDialogFragment"

        fun show(
            vararg mediaStoreMedia: MediaStoreMedia,
            manager: FragmentManager,
            positiveButtonClickListener: DialogInterface.OnClickListener
        ) {
            DeleteDialogFragment(mediaStoreMedia.size, positiveButtonClickListener)
                .show(manager, TAG)
        }
    }

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.calyx_dialog_delete, null)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getQuantityString(R.plurals.delete_confirmation_title, size))
            .setMessage(resources.getQuantityString(R.plurals.delete_confirmation_message, size))
            .setView(view)
            .setPositiveButton(getString(android.R.string.ok), positiveButtonClickListener)
            .setNegativeButton(getString(android.R.string.cancel)) { _, _ -> dialog?.dismiss() }
            .create()
    }

    override fun onResume() {
        super.onResume()
        dialog?.findViewById<CheckBox>(R.id.checkBox)?.apply {
            text = resources.getQuantityString(R.plurals.delete_confirmation_permanent, size)
            isChecked = sharedPreferences.permanentlyDeleteFiles
            setOnCheckedChangeListener { _, isChecked ->
                sharedPreferences.permanentlyDeleteFiles = isChecked
            }
        }
    }
}
