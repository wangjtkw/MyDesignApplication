package com.example.mydesignapplication.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydesignapplication.R
import com.example.mydesignapplication.publicclass.ViewHolder

class AlbumRecyclerAdapter : RecyclerView.Adapter<ViewHolder>() {
    private var mAlbumBeanList = ArrayList<AlbumBean>()
    private var mCallback: (AlbumBean) -> Unit = {}

    fun setData(albumBeanList: ArrayList<AlbumBean>) {
        mAlbumBeanList = albumBeanList
    }

    fun setCallback(callback: (AlbumBean) -> Unit) {
        mCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_album_recycler, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setImage(mAlbumBeanList[position].DATA!!, R.id.item_album_recycler_image)
            .setListener({ mCallback(mAlbumBeanList[position]) }, R.id.item_album_recycler_image)
    }

    override fun getItemCount(): Int {
        return mAlbumBeanList.size
    }
}