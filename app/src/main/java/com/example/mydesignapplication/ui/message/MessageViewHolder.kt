package com.example.mydesignapplication.ui.message

import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.mydesignapplication.common.DataBindingViewHolder
import com.example.mydesignapplication.data.bean.RecordInfoResponse
import com.example.mydesignapplication.databinding.ItemMessagRvBinding
import com.example.mydesignapplication.databinding.ItemRecordsInfoBinding
import com.example.mydesignapplication.ui.chatting.ChattingActivity
import com.example.mydesignapplication.ui.chatting.ChattingRequestParam

class MessageViewHolder(view: View) : DataBindingViewHolder<MessageBean>(view) {
    private val mBinding: ItemMessagRvBinding = DataBindingUtil.bind(view)!!
    override fun bindData(data: MessageBean, position: Int) {
        mBinding.messageBean = data
        mBinding.messageLayout.setOnClickListener {
            val chattingRequestBean = ChattingRequestParam(
                data.userAccountId,
                data.userName
            )
            ChattingActivity.startAction(context(), chattingRequestBean)
        }
    }

}
