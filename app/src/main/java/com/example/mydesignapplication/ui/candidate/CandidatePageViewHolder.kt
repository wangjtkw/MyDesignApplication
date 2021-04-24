package com.example.mydesignapplication.ui.candidate

import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.mydesignapplication.common.DataBindingViewHolder
import com.example.mydesignapplication.data.bean.RecordInfoResponse
import com.example.mydesignapplication.databinding.ItemRecordsInfoBinding
import com.example.mydesignapplication.ext.isConnectedNetwork
import com.example.mydesignapplication.ui.chatting.ChattingActivity
import com.example.mydesignapplication.ui.chatting.ChattingRequestParam
import com.example.mydesignapplication.utils.TimeUtil
import com.example.mydesignapplication.utils.ToastUtil

class CandidatePageViewHolder(view: View) : DataBindingViewHolder<RecordInfoResponse>(view) {
    private val mBinding: ItemRecordsInfoBinding = DataBindingUtil.bind(view)!!

    override fun bindData(data: RecordInfoResponse, position: Int) {
        mBinding.apply {
            val age = TimeUtil.getAge(data.users!!.usersBirthday!!)
            val sex = data.users!!.usersSex
            val role = data.users!!.usersRole
            val mInfo = "$sex | $age | $role"
            head = data.users!!.usersImg
            jobPosition = data.position!!.employerPositionTitle
            status = data.records!!.recordStateEmployer
            name = data.users!!.usersName
            info = mInfo
            phone = data.users!!.usersPhoneNum
            if ("待录取" == data.records!!.recordStateEmployer) {
                isSignUp = true
                isSettlement = false
            } else if ("待结算" == data.records!!.recordStateEmployer) {
                isSignUp = false
                isSettlement = true
            } else if ("已拒绝" == data.records!!.recordStateEmployer) {
                isSignUp = false
                isSettlement = false
//                state = 3
//                mBinding.apply {
//                    settlementButton.visibility = View.GONE
//                    signUpButton.visibility = View.GONE
//                    giveUpButton.visibility = View.GONE
//                }
            }
            onlineCommunicateButton.setOnClickListener {
                val receiveId = data.records!!.usersAccountId!!
                val receiveName = data.users!!.usersName!!
                val chattingRequestParam = ChattingRequestParam(receiveId, receiveName)
                ChattingActivity.startAction(context(), chattingRequestParam)
            }
            invalidateAll()
        }
    }

    fun removeItem(callback: () -> Unit) {
        mBinding.giveUpButton.setOnClickListener {
            if (context().isConnectedNetwork()) {
                ToastUtil.makeToast("移除项")
                callback()
            } else {
                ToastUtil.makeToast("当前网络未连接！")
            }

        }
    }

    fun signUpItem(callback: () -> Unit) {
        mBinding.signUpButton.setOnClickListener {
            if (context().isConnectedNetwork()) {
                callback()
            } else {
                ToastUtil.makeToast("当前网络未连接！")
            }
        }
    }

    fun settlementItem(callback: () -> Unit) {
        mBinding.settlementButton.setOnClickListener {
            if (context().isConnectedNetwork()) {
                callback()
            } else {
                ToastUtil.makeToast("当前网络未连接！")
            }
        }
    }

}