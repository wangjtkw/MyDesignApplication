package com.example.mydesignapplication.base

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mydesignapplication.dialog.AccessPermissionDialogUtil


abstract class PermissionActivity : AppCompatActivity() {
    private val TAG = "PermissionActivity"
    private val code = 0x1234
    private var currentPermission = ""
    protected var dialog: AlertDialog? = null

//    protected fun addAndCheckPermission(permission: String) {
//        checkPermission(permission)
//    }
//
//    protected fun addAndCheckPermissions(permissions: Array<out String>) {
//        for (i: Int in 0 until permissions.size) {
//            checkPermission(permissions[i])
//        }
//    }

    protected fun checkPermission(permission: String, tag: Int = 0) {
        val i = ContextCompat.checkSelfPermission(this, permission)
        if (i != PackageManager.PERMISSION_GRANTED) {
            getPermission(permission)
        } else {
            doOnGetPermission(permission, tag)
        }
    }

    open fun doOnGetPermission(permission: String, tag: Int = 0) {


    }

    private fun getPermission(permission: String) {
        currentPermission = permission
        ActivityCompat.requestPermissions(this, arrayOf(permission), code)
    }

    open fun showDialogTipUserGoToAppSetting(permission: String) {
        if (permission == Manifest.permission.READ_EXTERNAL_STORAGE) {
            val accessPermissionDialogUtil = AccessPermissionDialogUtil()
            accessPermissionDialogUtil.initAccessPermissionDialog(this, {
                gotoAppSetting()
            }, {}).show()
        }
        if (permission == Manifest.permission.CAMERA) {
            val accessPermissionDialogUtil = AccessPermissionDialogUtil()
            accessPermissionDialogUtil.initAccessPermissionDialog(this, {
                gotoAppSetting()
            }, {}).setTitle("获取相机权限").setMessage("请在-应用设置-权限-中，允许应用使用相机，来使应用能进行拍照")
                .show()
        }
    }

    protected fun gotoAppSetting() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        Log.d(TAG, "gotoAppSetting")
        startActivityForResult(intent, code)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requestCode == code && grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                val b = shouldShowRequestPermissionRationale(permissions[0])
                Log.d("aaaa", b.toString())
                if (!b) {
                    Log.d("aaaa", "run")
                    showDialogTipUserGoToAppSetting(permissions[0])
                } else {
                    currentPermission = ""
                }
            } else {
                doOnGetPermission(currentPermission)
                currentPermission = ""

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requestCode == code) {
                val i = ContextCompat.checkSelfPermission(this, currentPermission)
                if (i != PackageManager.PERMISSION_GRANTED) {
                    showDialogTipUserGoToAppSetting(currentPermission)
                } else {
                    if (dialog != null && dialog!!.isShowing) {
                        dialog!!.dismiss()
                    }
                    doOnGetPermission(currentPermission)
                    currentPermission = ""

                }
            }
        }
    }


}