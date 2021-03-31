package com.example.mydesignapplication.ui.persionalinfo

import android.util.Log
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
import javax.inject.Inject

class PersonalInfoViewModel @Inject constructor(
    private val personalInfoDataSource: PersonalInfoDataSource,
    private val loginDataSource: LoginDataSource
) : ViewModel() {
    private val TAG = "PersonalInfoViewModel"

    val insertPersonalInfoResult = MediatorLiveData<ApiResponse<MyResponse<EmployerAccountBean>>>()
    val updatePersonalInfoResult = MediatorLiveData<ApiResponse<MyResponse<PersonalInfoBean>>>()

    val updatePersonalInfoHeadImgResult = MediatorLiveData<ApiResponse<MyResponse<Any>>>()
    val updatePersonalInfoFrontImgResult = MediatorLiveData<ApiResponse<MyResponse<Any>>>()
    val updatePersonalInfoBackImgResult = MediatorLiveData<ApiResponse<MyResponse<Any>>>()


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
            return
        }
        if (frontImg == null) {
            makeToast("身份证正面还未选择！")
            return
        }
        if (backImg == null) {
            makeToast("身份证背面还未选择！")
            return
        }
        if (name.isEmpty()) {
            makeToast("请输入您的姓名！")
            return
        }
        if (idNum.isEmpty()) {
            makeToast("请输入您的身份证号！")
            return
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
        name: String,
        idNum: String,
        callback: () -> Unit
    ) {
        if (name.isEmpty()) {
            makeToast("请输入您的姓名！")
            return
        }
        if (idNum.isEmpty()) {
            makeToast("请输入您的身份证号！")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val result = personalInfoDataSource.updatePersonalInfo(
                name, idNum, personalInfoId
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

    fun updatePersonalInfoHeadImg(headImg: AlbumBean?, personalInfoId: Int) {
        if (headImg == null) {
            makeToast("头像还未选择！")
            return
        }
        val httpUtil = HttpUtil()
        httpUtil.apply {
            addParameter("personalId", personalInfoId)
        }
        val headImgPart = httpUtil.buildFile("headImg", headImg.DATA!!)
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                personalInfoDataSource.updatePersonalInfoHeadImg(headImgPart, httpUtil.params)
            launch(Dispatchers.Main) {
                updatePersonalInfoHeadImgResult.addSource(result) {
                    when (it) {
                        is ApiSuccessResponse -> {
                            if (it.body.code != 200) {
                                makeToast("发生错误：${it.body.description}")
                            } else {
                                makeToast("上传成功")
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

    fun updatePersonalInfoFrontImg(frontImg: AlbumBean?, personalInfoId: Int) {
        if (frontImg == null) {
            makeToast("身份证正面还未选择！")
            return
        }
        val httpUtil = HttpUtil()
        httpUtil.apply {
            addParameter("personalId", personalInfoId)
        }
        val frontImgPart = httpUtil.buildFile("frontImg", frontImg.DATA!!)
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                personalInfoDataSource.updatePersonalInfoFrontImg(frontImgPart, httpUtil.params)
            launch(Dispatchers.Main) {
                updatePersonalInfoFrontImgResult.addSource(result) {
                    when (it) {
                        is ApiSuccessResponse -> {
                            if (it.body.code != 200) {
                                makeToast("发生错误：${it.body.description}")
                            } else {
                                makeToast("上传成功")
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

    fun updatePersonalInfoBackImg(backImg: AlbumBean?, personalInfoId: Int) {
        if (backImg == null) {
            makeToast("头像还未选择！")
            return
        }
        val httpUtil = HttpUtil()
        httpUtil.apply {
            addParameter("personalId", personalInfoId)
        }
        val backImgPart = httpUtil.buildFile("backImg", backImg.DATA!!)
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                personalInfoDataSource.updatePersonalInfoBackImg(backImgPart, httpUtil.params)
            launch(Dispatchers.Main) {
                updatePersonalInfoHeadImgResult.addSource(result) {
                    when (it) {
                        is ApiSuccessResponse -> {
                            if (it.body.code != 200) {
                                makeToast("发生错误：${it.body.description}")
                            } else {
                                makeToast("上传成功")
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