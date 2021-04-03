package com.example.mydesignapplication.data.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "position_info_bean")
@JsonClass(generateAdapter = true)
data class PositionInfoBean(
    @Json(name = "employerAccountId")
    val employerAccountId: Int,
    @Json(name = "employerPositionCity")
    val employerPositionCity: String,
    @Json(name = "employerPositionConnectInfo")
    val employerPositionConnectInfo: String,
    @Json(name = "employerPositionConnectType")
    val employerPositionConnectType: String,
    @Json(name = "employerPositionContent")
    val employerPositionContent: String,
    @Json(name = "employerPositionDate")
    val employerPositionDate: String,
    @PrimaryKey
    @Json(name = "employerPositionId")
    val employerPositionId: Int,
    @Json(name = "employerPositionImg")
    val employerPositionImg: String,
    @Json(name = "employerPositionIndustry")
    val employerPositionIndustry: String,
    @Json(name = "employerPositionPersonNum")
    val employerPositionPersonNum: String,
    @Json(name = "employerPositionPersonRequirements")
    val employerPositionPersonRequirements: String,
    @Json(name = "employerPositionPlace")
    val employerPositionPlace: String,
    @Json(name = "employerPositionSalary")
    val employerPositionSalary: String,
    @Json(name = "employerPositionSettlement")
    val employerPositionSettlement: String,
    @Json(name = "employerPositionState")
    val employerPositionState: String,
    @Json(name = "employerPositionTitle")
    val employerPositionTitle: String,
    @Json(name = "employerPositionWelfare")
    val employerPositionWelfare: String,
    @Json(name = "positionRequirementId")
    val positionRequirementId: Int
){
    override fun toString(): String {
        return "PositionInfoBean(employerAccountId=$employerAccountId, employerPositionCity='$employerPositionCity', employerPositionConnectInfo='$employerPositionConnectInfo', employerPositionConnectType='$employerPositionConnectType', employerPositionContent='$employerPositionContent', employerPositionDate='$employerPositionDate', employerPositionId=$employerPositionId, employerPositionImg='$employerPositionImg', employerPositionIndustry='$employerPositionIndustry', employerPositionPersonNum='$employerPositionPersonNum', employerPositionPersonRequirements='$employerPositionPersonRequirements', employerPositionPlace='$employerPositionPlace', employerPositionSalary='$employerPositionSalary', employerPositionSettlement='$employerPositionSettlement', employerPositionState='$employerPositionState', employerPositionTitle='$employerPositionTitle', employerPositionWelfare='$employerPositionWelfare', positionRequirementId=$positionRequirementId)"
    }
}