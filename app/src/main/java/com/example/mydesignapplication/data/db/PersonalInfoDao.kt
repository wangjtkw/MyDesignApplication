package com.example.mydesignapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mydesignapplication.data.bean.CompanyInfoBean
import com.example.mydesignapplication.data.bean.PersonalInfoBean

@Dao
interface PersonalInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonalInfo(personalInfoBean: PersonalInfoBean)

    @Query("SELECT * FROM personal_info_bean WHERE employerPersonalInfoId = :personalId")
    fun selectById(personalId: Int): LiveData<PersonalInfoBean>
}