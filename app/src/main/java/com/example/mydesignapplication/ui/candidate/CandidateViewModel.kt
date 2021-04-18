package com.example.mydesignapplication.ui.candidate

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydesignapplication.api.ApiEmptyResponse
import com.example.mydesignapplication.api.ApiErrorResponse
import com.example.mydesignapplication.api.ApiSuccessResponse
import com.example.mydesignapplication.data.bean.Status
import com.example.mydesignapplication.data.datasource.RecordsDataSource
import com.example.mydesignapplication.ui.MainActivity
import javax.inject.Inject

class CandidateViewModel @Inject constructor(
    private val dataSource: RecordsDataSource
) : ViewModel() {
    val isVisibleList = MutableLiveData<Boolean>(false)
    val getAllRecordResult = MediatorLiveData<Any>()


    fun getAllRecord(employerAccountId: Int) {
        val result = dataSource.getRecords(viewModelScope, employerAccountId)
        getAllRecordResult.addSource(result) {
            when (it.status) {
                Status.SUCCESS -> {
                    isVisibleList.value = it.data!!.isNotEmpty()
                }
            }
        }
    }

}