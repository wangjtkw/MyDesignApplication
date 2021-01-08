package com.example.mydesignapplication.releasejob.releasejob2

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydesignapplication.R

class WelfareRecyclerAdapter : RecyclerView.Adapter<WelfareViewHolder>() {
    private val mData = ArrayList<String>()
    private var count: Int = 0
    private var currentIndexes: ArrayList<Int> = ArrayList()
    private var initialSize = 5

    fun addData(data: List<String>): WelfareRecyclerAdapter {
        count = mData.size
        mData.addAll(data)
        notifyItemRangeChanged(count, data.size)
        Log.e("recycler", mData.size.toString())
        return this
    }

    fun setInitialSize(size: Int): WelfareRecyclerAdapter {
        initialSize = size
        return this
    }

    fun getIndexes(): ArrayList<Int> {
        return currentIndexes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelfareViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_activity_release_job2, parent, false)
        return WelfareViewHolder(view)
    }

    override fun onBindViewHolder(holder: WelfareViewHolder, position: Int) {
        holder.setListener({ ->
            setSelect(position)
            notifyDataSetChanged()
        }, R.id.item_activity_release_job2_layout)
        holder.setText(mData[position], R.id.item_activity_release_job2_text_view)
        if (position <= initialSize) {
            holder.setGone(R.id.item_activity_release_job2_image_view)
        }

        if (checkSelect(position)) {
            holder.setSelectTheme()
        } else {
            holder.setUnSelectTheme()
        }
    }

    private fun setSelect(index: Int) {
        if (currentIndexes.contains(index)) {
            currentIndexes.remove(index)
        } else {
            currentIndexes.add(index)
        }
    }

    private fun checkSelect(index: Int): Boolean {
        return currentIndexes.contains(index)
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}