package com.example.mydesignapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mydesignapplication.ui.message.ChattingBean

@Dao
interface ChattingBeanDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert()
    suspend fun insertChattingBean(chattingBean: ChattingBean)

    @Query("SELECT * FROM chatting_bean WHERE userAccountId = :userId AND employerAccountId = :employerAccountId")
    fun getChattingBeanById(userId: Int, employerAccountId: Int): LiveData<List<ChattingBean>>

    @Query("SELECT * FROM chatting_bean WHERE employerAccountId = :employerAccountId GROUP BY userAccountId")
    fun getChattingBeanById(employerAccountId: Int): LiveData<List<ChattingBean>>

}