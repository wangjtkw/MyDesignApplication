package com.example.mydesignapplication.netty

import com.example.mydesignapplication.data.db.EmployerDB
import com.example.mydesignapplication.ui.message.ChattingBean
import com.example.mydesignapplication.utils.AppHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object TCPDataSource {

    fun saveMessage(chattingBean: ChattingBean) {
        val db = EmployerDB.getInstance(AppHelper.mContext)
        GlobalScope.launch {
            db.chattingBeanDao().insertChattingBean(chattingBean)
        }
    }

}