package com.example.mydesignapplication.ui.releasejob.releasejob2

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import com.example.mydesignapplication.R
import com.example.mydesignapplication.publicclass.ViewHolder

class SettlementViewHolder(view: View) : ViewHolder(view) {

    fun setSelectTheme() {
        f<View>(R.id.item_activity_release_job2_layout).background =
            Color.parseColor("#FF87ef73").toDrawable()
        f<TextView>(R.id.item_activity_release_job2_text_view).setTextColor(Color.WHITE)
    }

    fun setUnSelectTheme() {
        f<View>(R.id.item_activity_release_job2_layout).background =
            Color.parseColor("#1A8F8F8F").toDrawable()
        f<TextView>(R.id.item_activity_release_job2_text_view).setTextColor(Color.parseColor("#DE000000"))
    }
}