package com.example.mydesignapplication.ui.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydesignapplication.R
import com.example.mydesignapplication.ui.candidate.CandidatePageViewHolder

class MessageRecyclerAdapter :
    RecyclerView.Adapter<MessageViewHolder>() {

    private val msgList = ArrayList<MessageBean>()

    fun addData(dataList: List<MessageBean>) {
        msgList.clear()
        msgList.addAll(dataList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_messag_rv, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bindData(msgList[position], position)

    }

    override fun getItemCount(): Int {
        return msgList.size
    }
}