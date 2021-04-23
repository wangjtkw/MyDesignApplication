package com.example.mydesignapplication.ui.chatting

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mydesignapplication.R
import com.example.mydesignapplication.data.bean.PersonalInfoBean
import com.example.mydesignapplication.data.bean.Users
import com.example.mydesignapplication.ui.message.ChattingBean


class ChattingRecyclerAdapter : RecyclerView.Adapter<ChattingViewHolder>() {
    private var mDiffer: AsyncListDiffer<ChattingBean>? = null

    private var userInfo: Users? = null
    private var employer: PersonalInfoBean? = null

    private val TAG = "ChattingRecyclerAdapter"

    private val diffCallback: DiffUtil.ItemCallback<ChattingBean> =
        object : DiffUtil.ItemCallback<ChattingBean>() {
            override fun areItemsTheSame(oldItem: ChattingBean, newItem: ChattingBean): Boolean {
                return TextUtils.equals(oldItem.key.toString(), newItem.key.toString())
            }

            override fun areContentsTheSame(oldItem: ChattingBean, newItem: ChattingBean): Boolean {
                return oldItem.msg == newItem.msg
            }
        }

    init {
        mDiffer = AsyncListDiffer(this, diffCallback)
    }

    fun setUser(users: Users) {
        userInfo = users
    }

    fun setEmployer(personalInfoBean: PersonalInfoBean) {
        employer = personalInfoBean
    }

    fun addData(dataList: List<ChattingBean>) {
        mDiffer!!.submitList(dataList)
//        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChattingViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chatting, parent, false)
        return ChattingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChattingViewHolder, position: Int) {
        holder.bindData(getItem(position), position)
        if (userInfo != null) {
            holder.setUser(userInfo!!)
        }
        if (employer != null) {
            holder.setEmployer(employer!!)
        }
    }

    fun getItem(position: Int): ChattingBean {
        return mDiffer!!.currentList[position]
    }

    override fun getItemCount(): Int {
        return mDiffer!!.currentList.size
    }
}