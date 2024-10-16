/*
 * SPDX-FileCopyrightText: 2024 The Calyx Institute
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.glimpse.calyx.ext

import android.content.SharedPreferences
import androidx.core.content.edit

// Files should be permanently deleted
private const val PERMANENTLY_DELETE_FILES_KEY = "permanently_delete_files_key"
private const val PERMANENTLY_DELETE_FILES_DEFAULT = true
var SharedPreferences.permanentlyDeleteFiles: Boolean
    get() = getBoolean(PERMANENTLY_DELETE_FILES_KEY, PERMANENTLY_DELETE_FILES_DEFAULT)
    set(value) = edit {
        putBoolean(PERMANENTLY_DELETE_FILES_KEY, value)
    }
