package com.example.mydesignapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mydesignapplication.data.bean.CompanyInfoBean
import com.example.mydesignapplication.data.bean.PositionInfoBean

@Dao
interface PositionInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPositionInfo(positionInfoBean: PositionInfoBean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPositionInfo(positionInfoBean: List<PositionInfoBean>)

    @Query("SELECT * FROM position_info_bean WHERE employerAccountId = :accountId AND employerPositionState = :status")
    fun selectALL(accountId: Int, status: String): LiveData<List<PositionInfoBean>>

}