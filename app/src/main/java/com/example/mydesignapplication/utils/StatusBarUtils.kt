package com.example.mydesignapplication.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager

object StatusBarUtils {

    fun setLightBar(activity: Activity, color:Int) {
        if (Build.VERSION.SDK_INT >= 21) {
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.decorView.systemUiVisibility =  window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = color
        }
    }

    fun setDarkBar(activity: Activity, color:Int) {
        if (Build.VERSION.SDK_INT >= 21) {
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.decorView.systemUiVisibility =  window.decorView.systemUiVisibility and  View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inc()
            window.statusBarColor = color
        }
    }
}