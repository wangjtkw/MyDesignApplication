package com.example.mydesignapplication.ui.chatting

import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.mydesignapplication.common.DataBindingViewHolder
import com.example.mydesignapplication.data.bean.PersonalInfoBean
import com.example.mydesignapplication.data.bean.Users
import com.example.mydesignapplication.databinding.ItemChattingBinding
import com.example.mydesignapplication.databinding.ItemMessagRvBinding
import com.example.mydesignapplication.ui.message.ChattingBean
import com.example.mydesignapplication.ui.message.MessageBean

class ChattingViewHolder(view: View) : DataBindingViewHolder<ChattingBean>(view) {
    private val mBinding: ItemChattingBinding = DataBindingUtil.bind(view)!!
    override fun bindData(data: ChattingBean, position: Int) {
        mBinding.chattingBean = data
    }

    fun setUser(users: Users) {
        mBinding.users = users
    }

    fun setEmployer(personalInfoBean: PersonalInfoBean) {
        mBinding.employer = personalInfoBean
    }
}