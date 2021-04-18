package com.example.mydesignapplication.ui.candidate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydesignapplication.api.ApiEmptyResponse
import com.example.mydesignapplication.api.ApiErrorResponse
import com.example.mydesignapplication.api.ApiSuccessResponse
import com.example.mydesignapplication.data.bean.RecordInfoResponse
import com.example.mydesignapplication.data.bean.RecordsEntity
import com.example.mydesignapplication.data.bean.Resource
import com.example.mydesignapplication.data.bean.Status
import com.example.mydesignapplication.data.datasource.RecordsDataSource
import com.example.mydesignapplication.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CandidatePageViewModel @Inject constructor(
    private val dataSource: RecordsDataSource
) : ViewModel() {

    val getRecordsListResult = MediatorLiveData<List<RecordInfoResponse>>()

    fun getRecordByType(
        employerAccountId: Int,
        type: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = dataSource.getRecordsType(employerAccountId, type)
            launch(Dispatchers.Main) {
                getRecordsListResult.addSource(result) {
                    when (it) {
                        is ApiSuccessResponse -> {
                            if (it.body.code != 200 || it.body.data == null) {
                                makeToast("发生错误：${it.body.description}")
                            } else {
                                getRecordsListResult.value = it.body.data!!
                            }
                        }
                        is ApiEmptyResponse -> {
                            makeToast("注册失败！")
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