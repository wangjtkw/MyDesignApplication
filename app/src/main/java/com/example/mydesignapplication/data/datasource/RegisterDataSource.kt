package com.example.mydesignapplication.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mydesignapplication.api.API
import com.example.mydesignapplication.api.ApiResponse
import com.example.mydesignapplication.data.bean.MyResponse
import com.example.mydesignapplication.data.bean.Resource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class RegisterDataSource @Inject constructor(private val api: API) {

    fun register(
        account: String,
        password: String
    ): LiveData<ApiResponse<MyResponse<Any>>> {
        return api.employerRegister(account,password)
    }

}