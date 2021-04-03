package com.example.mydesignapplication.ui.releasejob.releasejob4

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydesignapplication.api.ApiEmptyResponse
import com.example.mydesignapplication.api.ApiErrorResponse
import com.example.mydesignapplication.api.ApiResponse
import com.example.mydesignapplication.api.ApiSuccessResponse
import com.example.mydesignapplication.data.bean.MyResponse
import com.example.mydesignapplication.data.bean.PositionTempBean
import com.example.mydesignapplication.data.datasource.ReleaseJobDataSource
import com.example.mydesignapplication.utils.HttpUtil
import com.example.mydesignapplication.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

class ReleaseJob4ViewModel @Inject constructor(
    private val dataSource: ReleaseJobDataSource
) : ViewModel() {
    private val TAG = "ReleaseJob4ViewModel"
    val releaseJobResult = MediatorLiveData<ApiResponse<MyResponse<Any>>>()

    fun releaseJob(
        employerAccountId: Int,
        callback: () -> Unit
    ) {
        PositionTempBean.let {
            val employerPositionTitle = it.employerPositionTitle.value
            val employerPositionContent = it.employerPositionContent.value
            val employerPositionPersonNum = it.employerPositionPersonNum.value
            val employerPositionSalary =
                it.employerPositionSalary.value +"元/"+ it.employerPositionSalaryUnit.value
            val employerPositionSettlement = it.employerPositionSettlement.value
            val employerPositionWelfare = it.employerPositionWelfare.value
            val employerPositionPlace = it.employerPositionPlace.value
            val employerPositionDate = it.employerPositionDate.value
            val employerPositionConnectType = it.employerPositionConnectType.value
            val employerPositionConnectInfo = it.employerPositionConnectInfo.value
            val employerPositionPersonRequirements = it.employerPositionPersonalRequirement.value
            val employerPositionIndustry = it.employerPositionIndustry.value
            val employerPositionCity = it.employerPositionCity.value
            val addPositionRequirement = it.isNeedPersonRequirement.value
            val positionRequirementAge = it.positionRequirementAge.value
            val positionRequirementSex = it.positionRequirementSex.value
            val positionRequirementHeight = it.positionRequirementHeight.value
            val positionRequirementEducation = it.positionRequirementEducation.value

            Log.d(
                TAG,
                "employerPositionTitle:${employerPositionTitle} " +
                        "employerPositionContent:${employerPositionContent} " +
                        "employerPositionPersonNum:${employerPositionPersonNum}" +
                        "employerPositionSalary:${employerPositionSalary}" +
                        "employerPositionSettlement:${employerPositionSettlement}"
            )
            Log.d(
                TAG, "employerPositionWelfare:${employerPositionWelfare}" +
                        "employerPositionPlace:${employerPositionPlace}" +
                        "employerPositionDate:${employerPositionDate}" +
                        "employerPositionConnectType:${employerPositionConnectType}" +
                        "employerPositionConnectInfo:${employerPositionConnectInfo}"
            )
            Log.d(
                TAG, "employerPositionPersonRequirements:${employerPositionPersonRequirements}" +
                        "employerPositionIndustry:${employerPositionIndustry}" +
                        "employerPositionCity:${employerPositionCity}" +
                        "addPositionRequirement:${addPositionRequirement}"
            )

            val httpUtil = HttpUtil()
            httpUtil.apply {
                addParameter("employerAccountId", employerAccountId)
                addParameter("employerPositionTitle", employerPositionTitle!!)
                addParameter("employerPositionContent", employerPositionContent!!)
                addParameter("employerPositionPersonNum", employerPositionPersonNum!!)
                addParameter("employerPositionSalary", employerPositionSalary!!)
                addParameter("employerPositionSettlement", employerPositionSettlement!!)

                addParameter("employerPositionWelfare", employerPositionWelfare!!)
                addParameter("employerPositionPlace", employerPositionPlace!!)
                addParameter("employerPositionDate", employerPositionDate!!)
                addParameter("employerPositionConnectType", employerPositionConnectType!!)
                addParameter("employerPositionConnectInfo", employerPositionConnectInfo!!)

                addParameter(
                    "employerPositionPersonRequirements",
                    employerPositionPersonRequirements!!
                )
                addParameter("employerPositionIndustry", employerPositionIndustry!!)
                addParameter("employerPositionCity", employerPositionCity!!)

                addParameter("addPositionRequirement", addPositionRequirement!!)

                addParameter("positionRequirementAge", positionRequirementAge!!)
                addParameter("positionRequirementSex", positionRequirementSex!!)
                addParameter("positionRequirementHeight", positionRequirementHeight!!)
                addParameter("positionRequirementEducation", positionRequirementEducation!!)
            }
            val positionImgPart =
                httpUtil.buildFile(
                    "positionImg",
                    PositionTempBean.employerPositionImg.value!!.DATA!!
                )

            viewModelScope.launch(Dispatchers.IO) {
                val result = dataSource.releaseJob(
                    param = httpUtil.params,
                    positionImgPart
                )
                launch(Dispatchers.Main) {
                    releaseJobResult.addSource(result) { netRes ->
                        when (netRes) {
                            is ApiSuccessResponse -> {
                                if (netRes.body.code != 200) {
                                    makeToast("发生错误：${netRes.body.description}")
                                } else {
                                    makeToast("上传成功")
                                    callback()
                                }
                            }
                            is ApiEmptyResponse -> {
                                makeToast("保存失败！")
                            }
                            is ApiErrorResponse -> {
                                makeToast("发生错误：${netRes.errorMessage}")
                            }
                            else -> {
                                makeToast("失败！")
                            }
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