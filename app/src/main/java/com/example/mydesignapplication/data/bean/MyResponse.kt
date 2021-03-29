package com.example.mydesignapplication.data.bean

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyResponse<T>(
    val code: Int,
    val description: String?,
    val data: T?
)