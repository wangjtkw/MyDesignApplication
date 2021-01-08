package com.example.mydesignapplication.dialog

import android.content.Context

interface PermissionDialogUtil {
    fun initAccessPermissionDialog(
        context: Context,
        jumpCallback: () -> Unit,
        cancelCallback: () -> Unit = {}
    ): PermissionDialogUtil
}