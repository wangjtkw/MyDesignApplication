package com.example.mydesignapplication.data.bean

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Entity(tableName = "employer_account_bean")
@JsonClass(generateAdapter = true)
data class EmployerAccountBean(
    @Json(name = "employerAccountAccount")
    val employerAccountAccount: String?,
    @PrimaryKey
    @Json(name = "employerAccountId")
    val employerAccountId: Int,
    @Json(name = "employerAccountPassword")
    val employerAccountPassword: String?,
    @Json(name = "employerCompanyInfo")
    val employerCompanyInfo: Int?,
    @Json(name = "employerPersonalInfoId")
    val employerPersonalInfoId: Int?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(employerAccountAccount)
        parcel.writeInt(employerAccountId)
        parcel.writeString(employerAccountPassword)
        parcel.writeValue(employerCompanyInfo)
        parcel.writeValue(employerPersonalInfoId)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "EmployerAccountBean(employerAccountAccount=$employerAccountAccount, employerAccountId=$employerAccountId, employerAccountPassword=$employerAccountPassword, employerCompanyInfo=$employerCompanyInfo, employerPersonalInfoId=$employerPersonalInfoId)"
    }

    companion object CREATOR : Parcelable.Creator<EmployerAccountBean> {
        override fun createFromParcel(parcel: Parcel): EmployerAccountBean {
            return EmployerAccountBean(parcel)
        }

        override fun newArray(size: Int): Array<EmployerAccountBean?> {
            return arrayOfNulls(size)
        }
    }
}