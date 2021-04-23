package com.example.mydesignapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mydesignapplication.data.bean.*
import com.example.mydesignapplication.ui.message.ChattingBean

@Database(
    entities = [
        EmployerAccountBean::class,
        CompanyInfoBean::class,
        PersonalInfoBean::class,
        PositionInfoBean::class,
        RecordsEntity::class,
        ChattingBean::class
    ],
    version = 1,
    exportSchema = false
)
abstract class EmployerDB : RoomDatabase() {
    abstract fun employerAccountDao(): EmployerAccountDao
    abstract fun companyInfoDao(): CompanyInfoDao
    abstract fun personalInfoDao(): PersonalInfoDao
    abstract fun positionInfoDao(): PositionInfoDao
    abstract fun recordsDao(): RecordsDao
    abstract fun chattingBeanDao(): ChattingBeanDao

    companion object {

        @Volatile
        private var instance: EmployerDB? = null

        fun getInstance(context: Context): EmployerDB {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(application: Context): EmployerDB {
            return instance ?: Room.databaseBuilder(
                application,
                EmployerDB::class.java,
                "employer.db"
            )
                .fallbackToDestructiveMigration()//数据库更新时删除数据重新创建
                .build().also { this.instance = it }
        }
    }
}