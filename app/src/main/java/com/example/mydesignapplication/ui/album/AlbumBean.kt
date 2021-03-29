package com.example.mydesignapplication.ui.album

import android.os.Parcel
import android.os.Parcelable

data class AlbumBean(
    val DATA: String?,
    val DATE_ADDED: String?,
    val DATE_MODIFIED: String?,
    val DISPLAY_NAME: String?,
    val HEIGHT: String?,
    val MIME_TYPE: String?,
    val SIZE: String?,
    val TITLE: String?,
    val WIDTH: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun toString(): String {
        return "AlbumBean(DATA=$DATA, DATE_ADDED=$DATE_ADDED, DATE_MODIFIED=$DATE_MODIFIED, DISPLAY_NAME=$DISPLAY_NAME, HEIGHT=$HEIGHT, MIME_TYPE=$MIME_TYPE, SIZE=$SIZE, TITLE=$TITLE, WIDTH=$WIDTH)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(DATA)
        parcel.writeString(DATE_ADDED)
        parcel.writeString(DATE_MODIFIED)
        parcel.writeString(DISPLAY_NAME)
        parcel.writeString(HEIGHT)
        parcel.writeString(MIME_TYPE)
        parcel.writeString(SIZE)
        parcel.writeString(TITLE)
        parcel.writeString(WIDTH)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AlbumBean> {
        override fun createFromParcel(parcel: Parcel): AlbumBean {
            return AlbumBean(parcel)
        }

        override fun newArray(size: Int): Array<AlbumBean?> {
            return arrayOfNulls(size)
        }
    }
}