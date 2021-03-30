package com.example.mydesignapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mydesignapplication.data.bean.CompanyInfoBean
import com.example.mydesignapplication.data.bean.EmployerAccountBean
import com.example.mydesignapplication.data.bean.PersonalInfoBean

@Database(
    entities = [
        EmployerAccountBean::class,
        CompanyInfoBean::class,
        PersonalInfoBean::class
    ],
    version = 1,
    exportSchema = false
)
abstract class EmployerDB : RoomDatabase() {
    abstract fun employerAccountDao(): EmployerAccountDao
    abstract fun companyInfoDao(): CompanyInfoDao
    abstract fun personalInfoDao(): PersonalInfoDao
}