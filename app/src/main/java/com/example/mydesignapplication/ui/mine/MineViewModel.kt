package com.example.mydesignapplication.ui.mine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydesignapplication.data.datasource.PersonalInfoDataSource
import javax.inject.Inject

class MineViewModel @Inject constructor(
    private val personalInfoDataSource: PersonalInfoDataSource
) : ViewModel() {

    fun getPersonalInfo(personalInfoId: Int) =
        personalInfoDataSource.getPersonalInfo(viewModelScope, personalInfoId)

}