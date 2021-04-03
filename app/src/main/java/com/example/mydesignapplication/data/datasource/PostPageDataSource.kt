package com.example.mydesignapplication.data.datasource

import androidx.lifecycle.LiveData
import com.example.mydesignapplication.api.API
import com.example.mydesignapplication.api.ApiResponse
import com.example.mydesignapplication.data.bean.MyResponse
import com.example.mydesignapplication.data.bean.PersonalInfoBean
import com.example.mydesignapplication.data.bean.PositionInfoBean
import com.example.mydesignapplication.data.bean.Resource
import com.example.mydesignapplication.data.db.EmployerDB
import com.example.mydesignapplication.ext.isConnectedNetwork
import com.example.mydesignapplication.utils.AppHelper
import com.example.mydesignapplication.utils.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostPageDataSource @Inject constructor(
    private val api: API,
    private val db: EmployerDB
) {

    fun getPositionList(
        scope: CoroutineScope,
        accountId: Int,
        status: String
    ): LiveData<Resource<List<PositionInfoBean>>> {
        return object :
            ScopeDataSource<MyResponse<List<PositionInfoBean>>, List<PositionInfoBean>>(scope) {
            override suspend fun loadData(): LiveData<ApiResponse<MyResponse<List<PositionInfoBean>>>> {
                return api.getPositionList(accountId, status)
            }

            override fun loadFromDb(): LiveData<List<PositionInfoBean>> {
                return db.positionInfoDao().selectALL(accountId, status)
            }

            override fun shouldFetch(data: List<PositionInfoBean>?): Boolean {
                return AppHelper.mContext.isConnectedNetwork()
            }

            override suspend fun saveCallResult(item: MyResponse<List<PositionInfoBean>>) {
                if (item.code == 200 && item.data != null) {
                    db.positionInfoDao().insertPositionInfo(item.data)
                } else {
                    scope.launch(Dispatchers.Main) {
                        ToastUtil.makeToast("数据异常：${item.description}")
                    }
                }
            }

        }.asLiveData()
    }

}
