package com.example.mydesignapplication.data.datasource

import androidx.lifecycle.LiveData
import com.example.mydesignapplication.api.API
import com.example.mydesignapplication.api.ApiResponse
import com.example.mydesignapplication.data.bean.MyResponse
import com.example.mydesignapplication.data.bean.RecordInfoResponse
import com.example.mydesignapplication.data.bean.RecordsEntity
import com.example.mydesignapplication.data.bean.Resource
import com.example.mydesignapplication.data.db.EmployerDB
import com.example.mydesignapplication.ext.isConnectedNetwork
import com.example.mydesignapplication.utils.AppHelper
import com.example.mydesignapplication.utils.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecordsDataSource @Inject constructor(
    private val api: API,
    private val db: EmployerDB
) {

    fun getRecords(scope: CoroutineScope, employerAccountId: Int)
            : LiveData<Resource<List<RecordsEntity>>> {
        return object :
            ScopeDataSource<MyResponse<List<RecordsEntity>>, List<RecordsEntity>>(scope) {
            override suspend fun loadData(): LiveData<ApiResponse<MyResponse<List<RecordsEntity>>>> {
                return api.getRecords(employerAccountId)
            }

            override fun loadFromDb(): LiveData<List<RecordsEntity>> {
                return db.recordsDao().selectByEmployerAccountId(employerAccountId)
            }

            override fun shouldFetch(data: List<RecordsEntity>?): Boolean {
                return AppHelper.mContext.isConnectedNetwork()
            }

            override suspend fun saveCallResult(item: MyResponse<List<RecordsEntity>>) {
                if (item.code == 200 && !item.data.isNullOrEmpty()) {
                    db.recordsDao().insertRecordsEntity(item.data)
                } else {
                    scope.launch(Dispatchers.Main) {
                        ToastUtil.makeToast("出现异常：${item.description}")
                    }
                }
            }

        }.asLiveData()
    }

    fun getRecordsType(
        employerAccountId: Int,
        type: String
    ): LiveData<ApiResponse<MyResponse<List<RecordInfoResponse>>>> {
        return api.getRecordsType(employerAccountId, type)
    }


}