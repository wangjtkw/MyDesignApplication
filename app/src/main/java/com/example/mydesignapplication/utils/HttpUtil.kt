package com.example.mydesignapplication.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
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
            params[key] = body
        } else if (o is Int) {
            val body: RequestBody = RequestBody.create(
                "text/plain;charset=UTF-8".toMediaTypeOrNull(),
                o.toString()
            )
            params[key] = body
        }else if (o is Boolean){
            val body: RequestBody = RequestBody.create(
                "text/plain;charset=UTF-8".toMediaTypeOrNull(),
                o.toString()
            )
            params[key] = body
        }
    }

    fun buildFile(paramName: String, filePath: String?): MultipartBody.Part {
        val fileNameByTimeStamp: String = System.currentTimeMillis().toString() + ".jpg"
        val file: File = File(filePath)
        val requestFile: RequestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData(paramName, fileNameByTimeStamp, requestFile)
    }
}