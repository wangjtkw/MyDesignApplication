package com.example.mydesignapplication.data.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "personal_info_bean")
@JsonClass(generateAdapter = true)
data class PersonalInfoBean(
    @Json(name = "employerPersonalInfoHead")
    val employerPersonalInfoHead: String,
    @PrimaryKey
    @Json(name = "employerPersonalInfoId")
    val employerPersonalInfoId: Int,
    @Json(name = "employerPersonalInfoIdCardBack")
    val employerPersonalInfoIdCardBack: String,
    @Json(name = "employerPersonalInfoIdCardFront")
    val employerPersonalInfoIdCardFront: String,
    @Json(name = "employerPersonalInfoIdCardNum")
    val employerPersonalInfoIdCardNum: String,
    @Json(name = "employerPersonalInfoName")
    val employerPersonalInfoName: String
)