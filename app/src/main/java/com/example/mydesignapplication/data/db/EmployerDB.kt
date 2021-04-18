package com.example.mydesignapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mydesignapplication.data.bean.*

@Database(
    entities = [
        EmployerAccountBean::class,
        CompanyInfoBean::class,
        PersonalInfoBean::class,
        PositionInfoBean::class,
        RecordsEntity::class
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
}