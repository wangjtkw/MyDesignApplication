package com.example.mydesignapplication.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydesignapplication.data.bean.PositionInfoBean
import com.example.mydesignapplication.data.bean.Resource
import com.example.mydesignapplication.data.datasource.PostPageDataSource
import com.example.mydesignapplication.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostPageViewModel @Inject constructor(
    private val dataSource: PostPageDataSource
) : ViewModel() {

    fun getPositionList(
        accountId: Int,
        status: String
    ): LiveData<Resource<List<PositionInfoBean>>> {
        return dataSource.getPositionList(viewModelScope, accountId, status)
    }

}