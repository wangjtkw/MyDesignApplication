package com.example.mydesignapplication.ui.chatting

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydesignapplication.api.ApiEmptyResponse
import com.example.mydesignapplication.api.ApiErrorResponse
import com.example.mydesignapplication.api.ApiSuccessResponse
import com.example.mydesignapplication.data.bean.PersonalInfoBean
import com.example.mydesignapplication.data.bean.Status
import com.example.mydesignapplication.data.bean.Users
import com.example.mydesignapplication.data.datasource.PersonalInfoDataSource
import com.example.mydesignapplication.ui.message.ChattingBean
import com.example.mydesignapplication.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChattingViewModel @Inject constructor(
    private val chattingDataSource: ChattingDataSource,
    private val personalInfoDataSource: PersonalInfoDataSource
) : ViewModel() {

    val userResult = MediatorLiveData<Users>()
    val employerResult = MediatorLiveData<PersonalInfoBean>()
    val msgResult = MediatorLiveData<List<ChattingBean>>()


    fun getUserInfo(userAccountId: Int) {
        viewModelScope.launch {
            val result = chattingDataSource.getUserInfo(userAccountId)
            userResult.addSource(result) {
                when (it) {
                    is ApiSuccessResponse -> {
                        if (it.body.code != 200) {
                            makeToast("发生错误：${it.body.description}")
                        } else {
                            userResult.value = it.body.data!!
                        }
                    }
                    is ApiEmptyResponse -> {
                        makeToast("注册失败！")
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

    fun getEmployerInfo(employerAccountId: Int) {
        viewModelScope.launch {
            val result = personalInfoDataSource.getPersonalInfo(viewModelScope, employerAccountId)
            employerResult.addSource(result) {
                when (it.status) {
                    Status.SUCCESS -> {
                        employerResult.value = it.data!!
                    }
                }

            }
        }
    }

    fun getMsg(userAccountId: Int, employerAccountId: Int) {
        viewModelScope.launch {
            val result = chattingDataSource.getMsg(userAccountId, employerAccountId)
            msgResult.addSource(result) {
                msgResult.value = it
            }
        }
    }


    private fun makeToast(msg: String) {
        viewModelScope.launch(Dispatchers.Main) {
            ToastUtil.makeToast(msg)
        }
    }

}