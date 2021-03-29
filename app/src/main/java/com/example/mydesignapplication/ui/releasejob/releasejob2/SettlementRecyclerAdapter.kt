package com.example.mydesignapplication.ui.releasejob.releasejob2

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydesignapplication.R

class SettlementRecyclerAdapter : RecyclerView.Adapter<SettlementViewHolder>() {
    private val mData = ArrayList<String>()
    private var count: Int = 0
    private var currentIndex: Int = 2

    fun addData(data: List<String>) {
        count = mData.size
        mData.addAll(data)
        notifyItemRangeChanged(count, data.size)
        Log.e("recycler", mData.size.toString())
    }

    fun getIndex(): Int {
        return currentIndex
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettlementViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_activity_release_job2, parent, false)
        return SettlementViewHolder(view)
    }

    override fun onBindViewHolder(holder: SettlementViewHolder, position: Int) {
        holder.setListener({ ->
            currentIndex = position
            notifyDataSetChanged()
        }, R.id.item_activity_release_job2_layout)
        mData[position].let {
            holder.setText(it, R.id.item_activity_release_job2_text_view)
                .setGone(R.id.item_activity_release_job2_image_view)
        }
        if (position != currentIndex) {
            holder.setUnSelectTheme()
        } else {
            holder.setSelectTheme()
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}