package com.example.mydesignapplication.data.datasource

import androidx.lifecycle.LiveData
import com.example.mydesignapplication.api.API
import com.example.mydesignapplication.api.ApiResponse
import com.example.mydesignapplication.data.bean.*
import com.example.mydesignapplication.data.db.EmployerDB
import com.example.mydesignapplication.ext.isConnectedNetwork
import com.example.mydesignapplication.utils.AppHelper
import com.example.mydesignapplication.utils.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import javax.inject.Inject

class PersonalInfoDataSource @Inject constructor(
    private val api: API,
    private val db: EmployerDB
) {

    fun insertPersonalInfo(
        param: HashMap<String, RequestBody>,
        headImg: MultipartBody.Part,
        frontImg: MultipartBody.Part,
        backImg: MultipartBody.Part
    ): LiveData<ApiResponse<MyResponse<EmployerAccountBean>>> {
        return api.insertPersonalInfo(headImg, frontImg, backImg, param)
    }

    fun getPersonalInfo(
        scope: CoroutineScope,
        personalInfoId: Int
    ): LiveData<Resource<PersonalInfoBean>> {
        return object : ScopeDataSource<MyResponse<PersonalInfoBean>, PersonalInfoBean>(scope) {
            override suspend fun loadData(): LiveData<ApiResponse<MyResponse<PersonalInfoBean>>> {
                return api.getPersonalInfo(personalInfoId)
            }

            override fun loadFromDb(): LiveData<PersonalInfoBean> {
                return db.personalInfoDao().selectById(personalInfoId)
            }

            override fun shouldFetch(data: PersonalInfoBean?): Boolean {
                return AppHelper.mContext.isConnectedNetwork()
            }

            override suspend fun saveCallResult(item: MyResponse<PersonalInfoBean>) {
                if (item.code == 200 && item.data != null) {
                    db.personalInfoDao().insertPersonalInfo(item.data)
                } else {
                    scope.launch(Dispatchers.Main) {
                        ToastUtil.makeToast("数据异常：${item.description}")
                    }
                }
            }
        }.asLiveData()
    }

    fun updatePersonalInfo(
        name: String,
        idNum: String,
        personalInfoId: Int
    ): LiveData<ApiResponse<MyResponse<PersonalInfoBean>>> {
        return api.updatePersonalInfo(name, idNum, personalInfoId)
    }

    suspend fun updatePersonalInfoHeadImg(
        headImg: MultipartBody.Part,
        param: HashMap<String, RequestBody>,
    ): LiveData<ApiResponse<MyResponse<Any>>> {
        return api.updatePersonalInfoHeadImg(headImg, param)
    }

    suspend fun updatePersonalInfoFrontImg(
        frontImg: MultipartBody.Part,
        param: HashMap<String, RequestBody>,
    ): LiveData<ApiResponse<MyResponse<Any>>> {
        return api.updatePersonalInfoFrontImg(frontImg, param);
    }

    suspend fun updatePersonalInfoBackImg(
        backImg: MultipartBody.Part,
        param: HashMap<String, RequestBody>,
    ): LiveData<ApiResponse<MyResponse<Any>>> {
        return api.updatePersonalInfoBackImg(backImg, param);
    }

    fun updatePersonalInfoDB(scope: CoroutineScope, personalInfoBean: PersonalInfoBean) {
        scope.launch(Dispatchers.IO) {
            db.personalInfoDao().insertPersonalInfo(personalInfoBean)
        }
    }
}