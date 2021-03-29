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
import javax.inject.Inject

class CompanyInfoDataSource @Inject constructor(
    private val api: API,
    private val db: EmployerDB
) {

    fun insertCompanyInfo(
        companyInfoInsertBean: CompanyInfoInsertBean
    ): LiveData<ApiResponse<MyResponse<EmployerAccountBean>>> {
        return companyInfoInsertBean.run {
            api.insertCompanyInfo(
                accountId,
                employerCompanyInfoCompanyType,
                employerCompanyInfoBusinessState,
                employerCompanyInfoFoundTime,
                employerCompanyInfoRegisterAddress,
                employerCompanyInfoUniformSocialCreditCode,
                employerCompanyInfoOrganizationCode,
                employerCompanyInfoBusinessScope
            )
        }
    }

    fun getCompanyInfo(
        scope: CoroutineScope,
        companyInfoId: Int
    ): LiveData<Resource<CompanyInfoBean>> {
        return object : ScopeDataSource<MyResponse<CompanyInfoBean>, CompanyInfoBean>(scope) {
            override suspend fun loadData(): LiveData<ApiResponse<MyResponse<CompanyInfoBean>>> {
                return api.getCompanyInfo(companyInfoId)
            }

            override fun loadFromDb(): LiveData<CompanyInfoBean> {
                return db.companyInfoDao().selectByAccount(companyInfoId)
            }

            override fun shouldFetch(data: CompanyInfoBean?): Boolean {
                return AppHelper.mContext.isConnectedNetwork()
            }

            override suspend fun saveCallResult(item: MyResponse<CompanyInfoBean>) {
                if (item.code == 200 && item.data != null) {
                    db.companyInfoDao().insertCompanyInfo(item.data)
                } else {
                    scope.launch(Dispatchers.Main) {
                        ToastUtil.makeToast("数据异常：${item.description}")
                    }
                }
            }


        }.asLiveData()
    }

    fun updateCompanyInfo(
        companyInfoBean: CompanyInfoBean
    ): LiveData<ApiResponse<MyResponse<CompanyInfoBean>>> {
        return companyInfoBean.run {
            api.updateCompanyInfo(
                employerCompanyInfoId,
                employerCompanyInfoCompanyType,
                employerCompanyInfoBusinessState,
                employerCompanyInfoFoundTime,
                employerCompanyInfoRegisterAddress,
                employerCompanyInfoUniformSocialCreditCode,
                employerCompanyInfoOrganizationCode,
                employerCompanyInfoBusinessScope
            )
        }
    }

    fun updateCompanyInfoDB(scope: CoroutineScope, companyInfoBean: CompanyInfoBean) {
        scope.launch(Dispatchers.IO) {
            db.companyInfoDao().insertCompanyInfo(companyInfoBean)
        }
    }


}