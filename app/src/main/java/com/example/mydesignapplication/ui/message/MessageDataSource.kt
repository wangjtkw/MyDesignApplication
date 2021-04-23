package com.example.mydesignapplication.ui.message

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mydesignapplication.api.API
import com.example.mydesignapplication.api.ApiResponse
import com.example.mydesignapplication.data.bean.MyResponse
import com.example.mydesignapplication.data.bean.Users
import com.example.mydesignapplication.data.db.EmployerDB
import javax.inject.Inject

class MessageDataSource @Inject constructor(
    private val api: API,
    private val db: EmployerDB
) {

    private val TAG = "MessageDataSource"

    suspend fun getMessage(employerId: Int): LiveData<List<ChattingBean>> {
        return db.chattingBeanDao().getChattingBeanById(employerId)
    }

    fun getUserInfo(userAccountId: Int): LiveData<ApiResponse<MyResponse<Users>>> {
        Log.d(TAG,"run")
        return api.getUserInfo(userAccountId)
    }


}