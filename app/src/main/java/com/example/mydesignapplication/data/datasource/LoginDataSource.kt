package com.example.mydesignapplication.data.datasource

import androidx.lifecycle.LiveData
import com.example.mydesignapplication.api.API
import com.example.mydesignapplication.api.ApiResponse
import com.example.mydesignapplication.api.ApiSuccessResponse
import com.example.mydesignapplication.data.bean.EmployerAccountBean
import com.example.mydesignapplication.data.bean.MyResponse
import com.example.mydesignapplication.data.bean.Resource
import com.example.mydesignapplication.data.db.EmployerDB
import com.example.mydesignapplication.ext.isConnectedNetwork
import com.example.mydesignapplication.utils.AppHelper
import com.example.mydesignapplication.utils.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val api: API,
    private val db: EmployerDB
) {

    fun login(
        scope: CoroutineScope,
        account: String,
        password: String
    ): LiveData<Resource<EmployerAccountBean>> {
        return object :
            ScopeDataSource<MyResponse<EmployerAccountBean>, EmployerAccountBean>(scope) {
            override suspend fun loadData(): LiveData<ApiResponse<MyResponse<EmployerAccountBean>>> {
                return api.employerLogin(account, password)
            }

            override fun loadFromDb(): LiveData<EmployerAccountBean> {
                return db.employerAccountDao().selectByAccount(account)
            }

            override fun shouldFetch(data: EmployerAccountBean?): Boolean {
                return AppHelper.mContext.isConnectedNetwork()
            }

            override suspend fun saveCallResult(item: MyResponse<EmployerAccountBean>) {
                if (item.code == 200 && item.data != null) {
                    db.employerAccountDao().insertEmployerAccount(item.data)
                } else {
                    scope.launch(Dispatchers.Main) {
                        ToastUtil.makeToast("登录异常：${item.description}")
                    }
                }
            }

        }.asLiveData()
    }

    fun insertAccount(scope: CoroutineScope, employerAccountBean: EmployerAccountBean) {
        scope.launch(Dispatchers.IO) {
            db.employerAccountDao().insertEmployerAccount(employerAccountBean)
        }
    }
}