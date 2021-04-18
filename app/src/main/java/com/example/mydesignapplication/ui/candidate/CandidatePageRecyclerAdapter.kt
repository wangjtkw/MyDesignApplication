package com.example.mydesignapplication.ui.candidate

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydesignapplication.R
import com.example.mydesignapplication.data.bean.RecordInfoResponse

class CandidatePageRecyclerAdapter(
    private val removeCallback: (Int) -> Unit,
    private val signUpCallback: (Int) -> Unit,
    private val settlementCallback: (Int) -> Unit,
) :
    RecyclerView.Adapter<CandidatePageViewHolder>() {
    private val mData = ArrayList<RecordInfoResponse>()
    private var count: Int = 0

    fun addData(data: List<RecordInfoResponse>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
        Log.e("recycler", mData.size.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidatePageViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_records_info, parent, false)
        return CandidatePageViewHolder(view)
    }

    override fun onBindViewHolder(holder: CandidatePageViewHolder, position: Int) {
        holder.bindData(mData[position], position)
        val recordId = mData[position].records!!.recordId!!
        holder.removeItem {
            mData.remove(mData[position])
            removeCallback(recordId)
            notifyItemRemoved(position)
        }
        holder.signUpItem {
            signUpCallback(recordId)
        }
        holder.settlementItem {
            settlementCallback(recordId)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}