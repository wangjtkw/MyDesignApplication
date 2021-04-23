package com.example.mydesignapplication.ui.message

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chatting_bean")
data class ChattingBean(
    val userAccountId: Int,
    val employerAccountId: Int,
    val msg: String,
    val timeStamp: Long,
    val isCurrentSend: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var key: Int = 0
    override fun toString(): String {
        return "ChattingBean(userAccountId=$userAccountId, employerAccountId=$employerAccountId, msg='$msg', timeStamp=$timeStamp, isCurrentSend=$isCurrentSend, key=$key)"
    }


}