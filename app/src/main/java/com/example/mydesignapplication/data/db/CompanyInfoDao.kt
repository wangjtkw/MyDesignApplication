package com.example.mydesignapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mydesignapplication.data.bean.CompanyInfoBean

@Dao
interface CompanyInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyInfo(companyInfoBean: CompanyInfoBean)

    @Query("SELECT * FROM company_info_bean WHERE employerCompanyInfoId = :companyInfoId")
    fun selectByAccount(companyInfoId: Int): LiveData<CompanyInfoBean>


}