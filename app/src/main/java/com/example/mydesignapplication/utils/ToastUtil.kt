package com.example.mydesignapplication.utils

import android.annotation.SuppressLint
import android.widget.Toast

object ToastUtil {
    private var lastShowTime = 0L
    private var lastShowMsg: String? = null
    private var curShowMsg: String? = null
    private var TOAST_DURATION = 2000
    private var toast: Toast? = null

    @SuppressLint("ShowToast")
    fun makeToast(message: CharSequence) {
        curShowMsg = message.toString()
        val currentShowTime = System.currentTimeMillis()
        val duration = currentShowTime - lastShowTime
        if (curShowMsg.equals(lastShowMsg) && duration <= TOAST_DURATION) {
            return
        }
        lastShowTime = currentShowTime
        lastShowMsg = curShowMsg
        if (toast == null) {
            toast = Toast.makeText(AppHelper.mContext, curShowMsg, Toast.LENGTH_SHORT)
        } else {
            toast?.setText(curShowMsg)
        }
        toast?.show()
    }
}