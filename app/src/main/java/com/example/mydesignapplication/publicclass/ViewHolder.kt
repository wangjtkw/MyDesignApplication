package com.example.mydesignapplication.publicclass

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


open class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun setListener(callback: () -> Unit, id: Int): ViewHolder {
        f<View>(id).setOnClickListener { callback() }
        return this
    }

    fun setVisible(id: Int) {
        f<View>(id).visibility = View.VISIBLE
    }

    fun setGone(id: Int) {
        f<View>(id).visibility = View.GONE
    }

    fun setViewVisible(visible: Boolean, id: Int) {
        if (visible) {
            setVisible(id)
        } else {
            setGone(id)
        }
    }

    fun setText(text: String, id: Int): ViewHolder {
        f<TextView>(id).text = text
        return this
    }

    fun setImage(src: String, id: Int): ViewHolder {
        val options = RequestOptions.bitmapTransform(RoundedCorners(20))
        Glide.with(view).load(src).apply(options).into(f(id))
        return this
    }

    protected fun <T : View> f(id: Int): T {
        return view.findViewById(id)
    }

}