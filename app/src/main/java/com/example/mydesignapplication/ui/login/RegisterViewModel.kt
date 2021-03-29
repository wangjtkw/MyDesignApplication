package com.example.mydesignapplication.ui.login

import android.util.Log
import androidx.lifecycle.*
import com.example.mydesignapplication.api.ApiEmptyResponse
import com.example.mydesignapplication.api.ApiErrorResponse
import com.example.mydesignapplication.api.ApiResponse
import com.example.mydesignapplication.api.ApiSuccessResponse
import com.example.mydesignapplication.data.bean.MyResponse
import com.example.mydesignapplication.data.bean.Resource
import com.example.mydesignapplication.data.datasource.RegisterDataSource
import com.example.mydesignapplication.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val registerDataSource: RegisterDataSource
) : ViewModel() {
    private val TAG = "RegisterViewModel"
    val registerResult = MediatorLiveData<ApiResponse<MyResponse<Any>>>()


    fun register(
        account: String,
        password: String,
        passwordAgain: String,
        callback: () -> Unit
    ) {
        when {
            account.isEmpty() -> {
                makeToast("账号不能为空")
                return
//                return MutableLiveData<Resource<MyResponse<Any>>>()
            }
            password.isEmpty() -> {
                makeToast("密码不能为空")
                return
//                return MutableLiveData<Resource<MyResponse<Any>>>()
            }
            passwordAgain.isEmpty() -> {
                makeToast("密码重复不能为空")
                return
//                return MutableLiveData<Resource<MyResponse<Any>>>()
            }
            password != passwordAgain -> {
                makeToast("两次输入密码不一致")
                return
//                return MutableLiveData<Resource<MyResponse<Any>>>()
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            val result = registerDataSource.register(
                account,
                password
            )
            launch(Dispatchers.Main) {
                registerResult.addSource(result) {
                    when (it) {
                        is ApiSuccessResponse -> {
                            if (it.body.code != 200) {
                                makeToast("发生错误：${it.body.description}")
                            } else {
                                callback()
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
    }

    private fun makeToast(msg: String) {
        viewModelScope.launch(Dispatchers.Main) {
            ToastUtil.makeToast(msg)
        }
    }

}