package com.example.mydesignapplication.binding

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.mydesignapplication.R
import com.example.mydesignapplication.constants.BASE_URL

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        val url = "${BASE_URL}uploadimg/${imageUrl}"
        Log.d("bindImageFromUrl", "url:$url")
        Glide.with(view.context)
            .load(url)
            .error(R.drawable.ic_head)
            .into(view)

    }
}