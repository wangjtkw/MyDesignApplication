package com.example.mydesignapplication.ui.companyInfo

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydesignapplication.api.ApiEmptyResponse
import com.example.mydesignapplication.api.ApiErrorResponse
import com.example.mydesignapplication.api.ApiResponse
import com.example.mydesignapplication.api.ApiSuccessResponse
import com.example.mydesignapplication.data.bean.CompanyInfoBean
import com.example.mydesignapplication.data.bean.CompanyInfoInsertBean
import com.example.mydesignapplication.data.bean.EmployerAccountBean
import com.example.mydesignapplication.data.bean.MyResponse
import com.example.mydesignapplication.data.datasource.CompanyInfoDataSource
import com.example.mydesignapplication.data.datasource.LoginDataSource
import com.example.mydesignapplication.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompanyInformationViewModel @Inject constructor(
    private val companyInfoDataSource: CompanyInfoDataSource,
    private val loginDataSource: LoginDataSource
) : ViewModel() {
    val insertCompanyInfoResult = MediatorLiveData<ApiResponse<MyResponse<EmployerAccountBean>>>()
    val updateCompanyInfoResult = MediatorLiveData<ApiResponse<MyResponse<CompanyInfoBean>>>()

    fun insertCompanyInfo(
        accountId: Int,
        companyType: String,
        businessState: String,
        foundTime: String,
        registerAddress: String,
        uniformSocialCreditCode: String,
        organizationCode: String,
        businessScope: String,
        callback: (EmployerAccountBean) -> Unit
    ) {
        if (companyType.isEmpty() || "请选择" == companyType) {
            ToastUtil.makeToast("请选择企业类型")
            return
        }

        if (businessState.isEmpty() || "请选择" == businessState) {
            ToastUtil.makeToast("请选择经营状态")
            return
        }
        if (foundTime.isEmpty() || "请选择" == foundTime) {
            ToastUtil.makeToast("请选择成立时间")
            return
        }
        if (registerAddress.isEmpty()) {
            ToastUtil.makeToast("请输入注册地址")
            return
        }
        if (uniformSocialCreditCode.isEmpty()) {
            ToastUtil.makeToast("请输入统一信用代码")
            return
        }
        if (organizationCode.isEmpty()) {
            ToastUtil.makeToast("请输入注册地址")
            return
        }
        if (businessScope.isEmpty()) {
            ToastUtil.makeToast("请输入经营范围")
            return
        }
        val comInfoBean = CompanyInfoInsertBean(
            accountId = accountId,
            employerCompanyInfoCompanyType = companyType,
            employerCompanyInfoBusinessState = businessState,
            employerCompanyInfoFoundTime = foundTime,
            employerCompanyInfoRegisterAddress = registerAddress,
            employerCompanyInfoUniformSocialCreditCode = uniformSocialCreditCode,
            employerCompanyInfoOrganizationCode = organizationCode,
            employerCompanyInfoBusinessScope = businessScope
        )
        viewModelScope.launch(Dispatchers.IO) {
            val result = companyInfoDataSource.insertCompanyInfo(comInfoBean)
            launch(Dispatchers.Main) {
                insertCompanyInfoResult.addSource(result) {
                    when (it) {
                        is ApiSuccessResponse -> {
                            if (it.body.code != 200 || it.body.data == null) {
                                makeToast("发生错误：${it.body.description}")
                            } else {
                                makeToast("保存成功")
                                loginDataSource.insertAccount(viewModelScope, it.body.data)
                                callback(it.body.data)
                            }
                        }
                        is ApiEmptyResponse -> {
                            makeToast("保存失败！")
                        }
                        is ApiErrorResponse -> {
                            makeToast("发生错误：${it.errorMessage}")
                        }
                        else -> {
                            makeToast("失败！")
                        }
                    }
                }
            }
        }
    }

    fun getCompanyInfo(companyInfoId: Int) =
        companyInfoDataSource.getCompanyInfo(viewModelScope, companyInfoId)

    fun updateCompanyInfo(
        companyInfoId: Int,
        companyType: String,
        businessState: String,
        foundTime: String,
        registerAddress: String,
        uniformSocialCreditCode: String,
        organizationCode: String,
        businessScope: String,
        callback: () -> Unit
    ) {
        if (companyType.isEmpty() || "请选择" == companyType) {
            ToastUtil.makeToast("请选择企业类型")
            return
        }

        if (businessState.isEmpty() || "请选择" == businessState) {
            ToastUtil.makeToast("请选择经营状态")
            return
        }
        if (foundTime.isEmpty() || "请选择" == foundTime) {
            ToastUtil.makeToast("请选择成立时间")
            return
        }
        if (registerAddress.isEmpty()) {
            ToastUtil.makeToast("请输入注册地址")
            return
        }
        if (uniformSocialCreditCode.isEmpty()) {
            ToastUtil.makeToast("请输入统一信用代码")
            return
        }
        if (organizationCode.isEmpty()) {
            ToastUtil.makeToast("请输入注册地址")
            return
        }
        if (businessScope.isEmpty()) {
            ToastUtil.makeToast("请输入经营范围")
            return
        }
        val comInfoBean = CompanyInfoBean(
            employerCompanyInfoId = companyInfoId,
            employerCompanyInfoCompanyType = companyType,
            employerCompanyInfoBusinessState = businessState,
            employerCompanyInfoFoundTime = foundTime,
            employerCompanyInfoRegisterAddress = registerAddress,
            employerCompanyInfoUniformSocialCreditCode = uniformSocialCreditCode,
            employerCompanyInfoOrganizationCode = organizationCode,
            employerCompanyInfoBusinessScope = businessScope,
            employerCompanyInfoAuditState = ""
        )
        viewModelScope.launch(Dispatchers.IO) {
            val result = companyInfoDataSource.updateCompanyInfo(comInfoBean)
            viewModelScope.launch(Dispatchers.Main) {
                updateCompanyInfoResult.addSource(result) {
                    when (it) {
                        is ApiSuccessResponse -> {
                            if (it.body.code != 200 || it.body.data == null) {
                                makeToast("发生错误：${it.body.description}")
                            } else {
                                makeToast("保存成功")
                                companyInfoDataSource.updateCompanyInfoDB(
                                    viewModelScope,
                                    it.body.data
                                )
                                callback()
                            }
                        }
                        is ApiEmptyResponse -> {
                            makeToast("保存失败！")
                        }
                        is ApiErrorResponse -> {
                            makeToast("发生错误：${it.errorMessage}")
                        }
                        else -> {
                            makeToast("失败！")
                        }
                    }
                }
            }
        }

    }

    private fun makeToast(msg: String) {
        viewModelScope.launch(Dispatchers.Main) {
            ToastUtil.makeToast(msg)
        }
    }
}