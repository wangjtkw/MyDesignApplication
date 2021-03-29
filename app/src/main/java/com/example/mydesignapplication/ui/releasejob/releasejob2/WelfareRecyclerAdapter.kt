package com.example.mydesignapplication.ui.releasejob.releasejob2

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydesignapplication.R

class WelfareRecyclerAdapter : RecyclerView.Adapter<WelfareViewHolder>() {
    private val mData = ArrayList<String>()
    private var count: Int = 0
    private var currentIndexes: ArrayList<String> = ArrayList()
    private var initialSize = 4

    fun addDataList(data: List<String>): WelfareRecyclerAdapter {
        count = mData.size
        mData.addAll(data)
        notifyItemRangeChanged(count, data.size)
        Log.e("recycler", mData.size.toString())
        return this
    }

    fun addData(data: String) {
        count = mData.size
        mData.add(data)
        notifyItemInserted(count)
    }

    fun setInitialSize(size: Int): WelfareRecyclerAdapter {
        initialSize = size
        return this
    }

    fun getIndexes(): ArrayList<String> {
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
            setSelect(mData[position])
            notifyDataSetChanged()
        }, R.id.item_activity_release_job2_layout)
        holder.setText(mData[position], R.id.item_activity_release_job2_text_view)
        if (position <= initialSize) {
            holder.setGone(R.id.item_activity_release_job2_image_view)
        } else {
            holder.setListener(
                { ->
                    if (currentIndexes.contains(mData[position])) {
                        currentIndexes.remove(mData[position])
                    }
                    mData.removeAt(position)
                    notifyItemRemoved(position)
                    notifyDataSetChanged()
                },
                R.id.item_activity_release_job2_image_view
            )
        }


        if (checkSelect(mData[position])) {
            holder.setSelectTheme()
        } else {
            holder.setUnSelectTheme()
        }
    }

    private fun setSelect(string: String) {
        if (currentIndexes.contains(string)) {
            currentIndexes.remove(string)
        } else {
            currentIndexes.add(string)
        }
    }

    private fun checkSelect(string: String): Boolean {
        return currentIndexes.contains(string)
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}