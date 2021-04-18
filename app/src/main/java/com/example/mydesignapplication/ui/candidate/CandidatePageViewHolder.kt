package com.example.mydesignapplication.ui.candidate

import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.mydesignapplication.common.DataBindingViewHolder
import com.example.mydesignapplication.data.bean.RecordInfoResponse
import com.example.mydesignapplication.databinding.ItemRecordsInfoBinding
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
            jobPosition = data.position!!.employerPositionTitle
            status = data.records!!.recordStateEmployer
            name = data.users!!.usersName
            info = mInfo
            phone = data.users!!.usersPhoneNum
            if ("待录取" == data.records!!.recordStateEmployer) {
                isSignUp = true
            } else if ("待结算" == data.records!!.recordStateEmployer) {
                isSignUp = false
            }
        }
    }

    fun removeItem(callback: () -> Unit) {
        mBinding.giveUpButton.setOnClickListener {
            ToastUtil.makeToast("移除项")
            callback()
        }
    }

    fun signUpItem(callback: () -> Unit) {
        mBinding.signUpButton.setOnClickListener {
            callback()
        }
    }

    fun settlementItem(callback: () -> Unit) {
        mBinding.settlementButton.setOnClickListener {
            callback()
        }
    }

}