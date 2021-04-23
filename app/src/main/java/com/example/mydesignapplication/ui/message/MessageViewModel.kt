package com.example.mydesignapplication.ui.message

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.mydesignapplication.api.ApiEmptyResponse
import com.example.mydesignapplication.api.ApiErrorResponse
import com.example.mydesignapplication.api.ApiSuccessResponse
import com.example.mydesignapplication.utils.TimeUtil
import com.example.mydesignapplication.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessageViewModel @Inject constructor(
    private val dataSource: MessageDataSource
) : ViewModel() {
    private val TAG = "MessageViewModel"

    //    val chattingBeanResult = MediatorLiveData<List<ChattingBean>>()
    val msgInfoResult = MediatorLiveData<List<MessageBean>>()
    val userInfoResult = MediatorLiveData<Any>()

    fun getInfo(employerId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val msgList = dataSource.getMessage(employerId)
            msgInfoResult.addSource(msgList) {
                Log.d(TAG, it.toString())
                val chattingMap = HashMap<Int, MessageBean>()
                it?.map { chatting ->
                    Log.d(TAG, chatting.toString())
                    val userAccountId = chatting.userAccountId
                    Log.d(TAG, "${!chattingMap.containsKey(userAccountId)}")
                    if (!chattingMap.containsKey(userAccountId)) {

                        Log.d(TAG, "run tets")
                        val userInfoResponse = dataSource.getUserInfo(userAccountId)

                        Log.d(TAG, userInfoResponse.toString())
                        userInfoResult.addSource(userInfoResponse) { userResponse ->
                            when (userResponse) {
                                is ApiSuccessResponse -> {
                                    if (userResponse.body.code != 200 || userResponse.body.data == null) {
                                        makeToast("发生错误：${userResponse.body.description}")
                                    } else {
                                        val time = TimeUtil.getTimeByFormat(chatting.timeStamp)
                                        Log.d(TAG, time)
                                        val messageBean = MessageBean(
                                            userAccountId = userAccountId,
                                            userHeadImg = userResponse.body.data.usersImg!!,
                                            userName = userResponse.body.data.usersName!!,
                                            time = time,
                                            msg = chatting.msg
                                        )
                                        chattingMap[userAccountId] = messageBean
                                        val result = ArrayList<MessageBean>()
                                        chattingMap.values.forEach { message ->
                                            result.add(message)
                                        }
                                        msgInfoResult.value = result
                                    }
                                }
                                is ApiEmptyResponse -> {
                                    makeToast("保存失败！")
                                }
                                is ApiErrorResponse -> {
                                    makeToast("发生错误：${userResponse.errorMessage}")
                                }
                                else -> {
                                    makeToast("失败！")
                                }
                            }
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