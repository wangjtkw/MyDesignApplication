package com.example.mydesignapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mydesignapplication.data.bean.RecordsEntity

@Dao
interface RecordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecordsEntity(recordsEntity: RecordsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecordsEntity(recordsEntityList: List<RecordsEntity>)

    @Query("SELECT * FROM records_entity WHERE employerAccountId = :employerAccountId")
    fun selectByEmployerAccountId(employerAccountId: Int): LiveData<List<RecordsEntity>>
}