package com.example.mydesignapplication.ui.persionalinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydesignapplication.api.ApiEmptyResponse
import com.example.mydesignapplication.api.ApiErrorResponse
import com.example.mydesignapplication.api.ApiResponse
import com.example.mydesignapplication.api.ApiSuccessResponse
import com.example.mydesignapplication.data.bean.EmployerAccountBean
import com.example.mydesignapplication.data.bean.MyResponse
import com.example.mydesignapplication.data.bean.PersonalInfoBean
import com.example.mydesignapplication.data.datasource.LoginDataSource
import com.example.mydesignapplication.data.datasource.PersonalInfoDataSource
import com.example.mydesignapplication.ui.album.AlbumBean
import com.example.mydesignapplication.utils.HttpUtil
import com.example.mydesignapplication.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

class PersonalInfoViewModel @Inject constructor(
    private val personalInfoDataSource: PersonalInfoDataSource,
    private val loginDataSource: LoginDataSource
) : ViewModel() {
    private val TAG = "PersonalInfoViewModel"

    val insertPersonalInfoResult = MediatorLiveData<ApiResponse<MyResponse<EmployerAccountBean>>>()
    val updatePersonalInfoResult = MediatorLiveData<ApiResponse<MyResponse<PersonalInfoBean>>>()

    fun insertPersonalInfo(
        accountId: Int,
        headImg: AlbumBean?,
        frontImg: AlbumBean?,
        backImg: AlbumBean?,
        name: String,
        idNum: String,
        callback: () -> Unit
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
        if (name.isEmpty()) {
            makeToast("请输入您的姓名！")
        }
        if (idNum.isEmpty()) {
            makeToast("请输入您的身份证号！")
        }
        val httpUtil = HttpUtil()
        httpUtil.apply {
            addParameter("name", name)
            addParameter("idNum", idNum)
            addParameter("accountId", accountId)
        }
        Log.d(TAG, "accountId:$accountId")
        val headImgPart = httpUtil.buildFile("headImg", headImg!!.DATA!!)
        val frontImgPart = httpUtil.buildFile("frontImg", frontImg!!.DATA!!)
        val backImgPart = httpUtil.buildFile("backImg", backImg!!.DATA!!)

        viewModelScope.launch(Dispatchers.IO) {
            val result = personalInfoDataSource.insertPersonalInfo(
                httpUtil.params,
                headImgPart,
                frontImgPart,
                backImgPart
            )
            launch(Dispatchers.Main) {
                insertPersonalInfoResult.addSource(result) {
                    when (it) {
                        is ApiSuccessResponse -> {
                            if (it.body.code != 200 || it.body.data == null) {
                                makeToast("发生错误：${it.body.description}")
                            } else {
                                makeToast("保存成功")
                                loginDataSource.insertAccount(viewModelScope, it.body.data)
                                callback()
                            }
                        }
                        is ApiEmptyResponse -> {
                            makeToast("保存失败！")
                        }
                        is ApiErrorResponse -> {
                            makeToast("发生错误：${it.errorMessage}")
                        }
                        else -> {
                            makeToast("失败！")
                        }
                    }
                }
            }
        }
    }

    fun getPersonalInfo(personalInfoId: Int) =
        personalInfoDataSource.getPersonalInfo(viewModelScope, personalInfoId)

    fun updatePersonalInfo(
        personalInfoId: Int,
        headImg: AlbumBean?,
        frontImg: AlbumBean?,
        backImg: AlbumBean?,
        name: String,
        idNum: String,
        callback: () -> Unit
    ) {
        if (name.isEmpty()) {
            makeToast("请输入您的姓名！")
        }
        if (idNum.isEmpty()) {
            makeToast("请输入您的身份证号！")
        }
        val httpUtil = HttpUtil()
        httpUtil.apply {
            addParameter("name", name)
            addParameter("idNum", idNum)
            addParameter("personalId", personalInfoId)
        }

        var headImgPart: MultipartBody.Part? = httpUtil.buildFile("headImg", headImg?.DATA)
        var frontImgPart: MultipartBody.Part? = httpUtil.buildFile("frontImg", frontImg?.DATA)
        var backImgPart: MultipartBody.Part? = httpUtil.buildFile("backImg", backImg?.DATA)
//        if (headImg != null) {
//            headImgPart =
//        }
//        if (frontImg != null) {
//            frontImgPart =
//        }
//        if (backImg != null) {
//            backImgPart =
//        }
        viewModelScope.launch(Dispatchers.IO) {
            val result = personalInfoDataSource.updatePersonalInfo(
                param = httpUtil.params,
                headImgPart, frontImgPart, backImgPart
            )
            launch(Dispatchers.Main) {
                updatePersonalInfoResult.addSource(result) {
                    when (it) {
                        is ApiSuccessResponse -> {
                            if (it.body.code != 200 || it.body.data == null) {
                                makeToast("发生错误：${it.body.description}")
                            } else {
                                makeToast("保存成功")
                                personalInfoDataSource.updatePersonalInfoDB(
                                    viewModelScope,
                                    it.body.data
                                )
                                callback()
                            }
                        }
                        is ApiEmptyResponse -> {
                            makeToast("保存失败！")
                        }
                        is ApiErrorResponse -> {
                            makeToast("发生错误：${it.errorMessage}")
                        }
                        else -> {
                            makeToast("失败！")
                        }
                    }
                }
            }
        }
    }



    private fun makeToast(msg: String) {
        viewModelScope.launch(Dispatchers.Main) {
            ToastUtil.makeToast(msg)
        }
    }
}