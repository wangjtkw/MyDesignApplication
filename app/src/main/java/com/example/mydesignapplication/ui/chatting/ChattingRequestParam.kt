package com.example.mydesignapplication.ui.chatting

import android.os.Parcel
import android.os.Parcelable

data class ChattingRequestParam(
    val receiverId: Int,
    val receiverName: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(receiverId)
        parcel.writeString(receiverName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChattingRequestParam> {
        override fun createFromParcel(parcel: Parcel): ChattingRequestParam {
            return ChattingRequestParam(parcel)
        }

        override fun newArray(size: Int): Array<ChattingRequestParam?> {
            return arrayOfNulls(size)
        }
    }
}