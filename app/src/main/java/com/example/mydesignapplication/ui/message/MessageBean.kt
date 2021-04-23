package com.example.mydesignapplication.ui.message

data class MessageBean(
    val userAccountId: Int,
    val userHeadImg: String,
    val userName: String,
    val time: String,
    val msg: String
) {
    override fun toString(): String {
        return "MessageBean(userAccountId=$userAccountId, userHeadImg='$userHeadImg', userName='$userName', time=$time, msg='$msg')"
    }
}