package com.example.mydesignapplication.data.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "records_entity")
@JsonClass(generateAdapter = true)
data class RecordsEntity(
    @Json(name = "employerAccountId")
    var employerAccountId: Int?,
    @Json(name = "employerPositionId")
    var employerPositionId: Int?,
    @PrimaryKey
    @Json(name = "recordId")
    var recordId: Int?,
    @Json(name = "recordStateEmployer")
    var recordStateEmployer: String?,
    @Json(name = "recordStateUser")
    var recordStateUser: String?,
    @Json(name = "usersAccountId")
    var usersAccountId: Int?
)