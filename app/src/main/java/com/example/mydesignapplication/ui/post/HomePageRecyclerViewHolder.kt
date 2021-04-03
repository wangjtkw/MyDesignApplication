package com.example.mydesignapplication.ui.post

import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.mydesignapplication.R
import com.example.mydesignapplication.common.DataBindingViewHolder
import com.example.mydesignapplication.data.bean.PositionInfoBean
import com.example.mydesignapplication.databinding.ItemPositionInfoBinding


class HomePageRecyclerViewHolder(view: View) : DataBindingViewHolder<PositionInfoBean>(view) {
    private val mBinding: ItemPositionInfoBinding = DataBindingUtil.bind(view)!!
    private val TAG = "HomePageRecyclerViewHolder"

    fun buildTagView(text: String): FrameLayout {
        val textView = TextView(view.context)
        textView.apply {
            setTextColor(Color.parseColor("#BFFF0057"))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            setPadding(10, 4, 10, 4)
            setText(text)
        }
        val frameLayout = FrameLayout(view.context)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            frameLayout.apply {
                background = view.context.getDrawable(R.drawable.shape_circle_corner_red)
                addView(textView)
                val lp = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                lp.setMargins(0, 0, 20, 0)
                setLayoutParams(lp)
            }
        }

        return frameLayout
    }

    override fun bindData(data: PositionInfoBean, position: Int) {
        Log.d(TAG,data.toString())
        mBinding.positionInfoBean = data
        val list = getWelfare(data.employerPositionWelfare)
        mBinding.itemHomePageTagLayout.apply {
            list.map {
                addView(buildTagView(it))
            }
        }
//        mBinding!!.itemHomePageTitle.text
//        holder.setText(it.employerPositionTitle, R.id.item_home_page_title)
//            .setText(it.company, R.id.item_home_page_company)
//            .setText(it.employerPositionSalary, R.id.item_home_page_salary)
//        holder.setViewVisible(it.img != null && it.img != "", R.id.item_home_page_image_view)
//        if (it.employerPositionImg != null && it.employerPositionImg != "") {
//            holder.setImage(it.img, R.id.item_home_page_image_view)
//        }
//
//        holder.getLayout(R.id.item_home_page_tag_layout).apply {
//            for (i in it.tags) {
//                addView(holder.buildTagView(i))
//            }
//        }
    }

    private fun getWelfare(str: String): ArrayList<String> {
        val strList = ArrayList<String>()
        if (str.isEmpty()) {
            return strList
        }
        var p = 0
        val indexList = ArrayList<Int>()
        while (p < str.length) {
            if (str[p] == '￥') {
                indexList.add(p)
            }
            p++
        }
        p = 0
        strList.add(str.substring(0, indexList[p]))
        while (p < indexList.size - 1) {
            strList.add(str.substring(indexList[p]+1, indexList[++p]))
        }
        strList.add(str.substring(indexList[p]+1, str.length))
        return strList
    }
}