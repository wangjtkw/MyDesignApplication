package com.example.mydesignapplication.ui.persionalinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydesignapplication.ui.album.AlbumBean
import com.example.mydesignapplication.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonalInfoViewModel : ViewModel() {

    fun insertPersonalInfo(
        accountId: Int,
        headImg: AlbumBean?,
        frontImg: AlbumBean?,
        backImg: AlbumBean?,
        name: String,
        idNum: String
    ) {
        if (headImg == null) {
            makeToast("头像还未选择！")
        }
        if (frontImg == null) {
            makeToast("身份证正面还未选择！")
        }
        if (backImg == null) {
            makeToast("身份证背面还未选择！")
        }
        if (name.isEmpty()){
            makeToast("请输入您的姓名！")
        }
        if (idNum.isEmpty()){
            makeToast("请输入您的身份证号！")
        }


    }

    private fun makeToast(msg: String) {
        viewModelScope.launch(Dispatchers.Main) {
            ToastUtil.makeToast(msg)
        }
    }
}