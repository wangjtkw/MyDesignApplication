package com.example.mydesignapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mydesignapplication.data.bean.EmployerAccountBean

@Dao
interface EmployerAccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployerAccount(employerAccountBean: EmployerAccountBean)

    @Query("SELECT * FROM employer_account_bean WHERE employerAccountAccount = :account")
    fun selectByAccount(account: String): LiveData<EmployerAccountBean>

    @Query("DELETE FROM employer_account_bean WHERE employerAccountAccount = :account")
    fun deleteByAccount(account: String)

}