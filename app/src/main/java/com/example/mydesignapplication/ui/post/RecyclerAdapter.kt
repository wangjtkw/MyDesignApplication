package com.example.mydesignapplication.ui.post

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydesignapplication.R
import com.example.mydesignapplication.data.bean.PositionInfoBean


class PostPageRecyclerAdapter : RecyclerView.Adapter<HomePageRecyclerViewHolder>() {
    private var mCallback: (() -> Unit)? = null
    private val mData = ArrayList<PositionInfoBean>()
    private var count: Int = 0

    fun addData(data: List<PositionInfoBean>) {
        count = mData.size
        mData.addAll(data)
        notifyItemRangeChanged(count, data.size)
        Log.e("recycler", mData.size.toString())
    }

    fun setCallBack(callback: (() -> Unit)) {
        mCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageRecyclerViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_position_info, parent, false)
//        if (mCallback == null){
//            throw Exception("${this.javaClass.simpleName} callback is")
//        }
        return HomePageRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomePageRecyclerViewHolder, position: Int) {
        holder.bindData(mData[position],position)
//        mData[position].let {
//            Log.e("recycler", it.company.toString())
//            holder.setText(it.employerPositionTitle, R.id.item_home_page_title)
//                .setText(it.company, R.id.item_home_page_company)
//                .setText(it.employerPositionSalary, R.id.item_home_page_salary)
//            holder.setViewVisible(it.img != null && it.img != "", R.id.item_home_page_image_view)
//            if (it.employerPositionImg != null && it.employerPositionImg != "") {
//                holder.setImage(it.img, R.id.item_home_page_image_view)
//            }
//
//            holder.getLayout(R.id.item_home_page_tag_layout).apply {
//                for (i in it.tags) {
//                    addView(holder.buildTagView(i))
//                }
//            }
//        }
    }


    override fun getItemCount() = mData.size
}

