package com.example.mydesignapplication.utils

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.File

class HttpUtil {
    val params = HashMap<String, RequestBody>()

    fun addParameter(key: String, o: Any) {
        if (o is String) {
            val body: RequestBody = RequestBody.create(
                "text/plain;charset=UTF-8".toMediaTypeOrNull(),
                o
            )
            params[key] = body
        } else if (o is File) {
            val body: RequestBody =
                RequestBody.create(
                    "multipart/form-data;charset=UTF-8".toMediaTypeOrNull(),
                    o as File
                )
            params.put(key + "\"; filename=\"" + (o as File).getName() + "", body)
        }
    }
}