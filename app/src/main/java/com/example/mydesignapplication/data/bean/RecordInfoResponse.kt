package com.example.mydesignapplication.data.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecordInfoResponse(
    @Json(name = "position")
    var position: Position?,
    @Json(name = "records")
    var records: Records?,
    @Json(name = "users")
    var users: Users?
)

@JsonClass(generateAdapter = true)
data class Position(
    @Json(name = "employerAccountId")
    var employerAccountId: Int?,
    @Json(name = "employerPositionCity")
    var employerPositionCity: String?,
    @Json(name = "employerPositionConnectInfo")
    var employerPositionConnectInfo: String?,
    @Json(name = "employerPositionConnectType")
    var employerPositionConnectType: String?,
    @Json(name = "employerPositionContent")
    var employerPositionContent: String?,
    @Json(name = "employerPositionDate")
    var employerPositionDate: String?,
    @Json(name = "employerPositionId")
    var employerPositionId: Int?,
    @Json(name = "employerPositionImg")
    var employerPositionImg: String?,
    @Json(name = "employerPositionIndustry")
    var employerPositionIndustry: String?,
    @Json(name = "employerPositionPersonNum")
    var employerPositionPersonNum: String?,
    @Json(name = "employerPositionPersonRequirements")
    var employerPositionPersonRequirements: String?,
    @Json(name = "employerPositionPlace")
    var employerPositionPlace: String?,
    @Json(name = "employerPositionSalary")
    var employerPositionSalary: String?,
    @Json(name = "employerPositionSettlement")
    var employerPositionSettlement: String?,
    @Json(name = "employerPositionState")
    var employerPositionState: String?,
    @Json(name = "employerPositionTitle")
    var employerPositionTitle: String?,
    @Json(name = "employerPositionWelfare")
    var employerPositionWelfare: String?,
    @Json(name = "positionRequirementId")
    var positionRequirementId: Int?
)

@JsonClass(generateAdapter = true)
data class Records(
    @Json(name = "employerAccountId")
    var employerAccountId: Int?,
    @Json(name = "employerPositionId")
    var employerPositionId: Int?,
    @Json(name = "recordId")
    var recordId: Int?,
    @Json(name = "recordStateEmployer")
    var recordStateEmployer: String?,
    @Json(name = "recordStateUser")
    var recordStateUser: String?,
    @Json(name = "usersAccountId")
    var usersAccountId: Int?
)

@JsonClass(generateAdapter = true)
data class Users(
    @Json(name = "curriculumVitaeId")
    var curriculumVitaeId: Int?,
    @Json(name = "educationExperiencesId")
    var educationExperiencesId: Int?,
    @Json(name = "userPersonalTagId")
    var userPersonalTagId: Int?,
    @Json(name = "usersBirthday")
    var usersBirthday: String?,
    @Json(name = "usersId")
    var usersId: Int?,
    @Json(name = "usersImg")
    var usersImg: String?,
    @Json(name = "usersName")
    var usersName: String?,
    @Json(name = "usersPhoneNum")
    var usersPhoneNum: String?,
    @Json(name = "usersQq")
    var usersQq: String?,
    @Json(name = "usersRole")
    var usersRole: String?,
    @Json(name = "usersSelfDescription")
    var usersSelfDescription: String?,
    @Json(name = "usersSex")
    var usersSex: String?,
    @Json(name = "usersWechat")
    var usersWechat: String?
)