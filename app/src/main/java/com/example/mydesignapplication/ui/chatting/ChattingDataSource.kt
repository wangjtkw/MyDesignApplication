package com.example.mydesignapplication.ui.chatting

import androidx.lifecycle.LiveData
import com.example.mydesignapplication.api.API
import com.example.mydesignapplication.api.ApiResponse
import com.example.mydesignapplication.data.bean.MyResponse
import com.example.mydesignapplication.data.bean.Users
import com.example.mydesignapplication.data.db.EmployerDB
import com.example.mydesignapplication.ui.message.ChattingBean
import javax.inject.Inject

class ChattingDataSource @Inject constructor(
    private val api: API,
    private val db: EmployerDB
) {

    suspend fun getUserInfo(userAccountId: Int): LiveData<ApiResponse<MyResponse<Users>>> {
        return api.getUserInfo(userAccountId)
    }

    fun getMsg(userAccountId: Int, employerAccountId: Int): LiveData<List<ChattingBean>> {
        return db.chattingBeanDao().getChattingBeanById(userAccountId, employerAccountId)
    }

}