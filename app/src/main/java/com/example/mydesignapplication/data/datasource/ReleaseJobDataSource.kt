package com.example.mydesignapplication.data.datasource

import androidx.lifecycle.LiveData
import com.example.mydesignapplication.api.API
import com.example.mydesignapplication.api.ApiResponse
import com.example.mydesignapplication.data.bean.MyResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ReleaseJobDataSource @Inject constructor(private val api: API) {

    fun releaseJob(
        param: HashMap<String, RequestBody>,
        positionImg: MultipartBody.Part,
    ): LiveData<ApiResponse<MyResponse<Any>>> {
        return api.releaseJob(positionImg, param)
    }
}