package com.example.mydesignapplication.data.datasource

import com.example.mydesignapplication.api.API
import com.example.mydesignapplication.data.db.EmployerDB
import javax.inject.Inject

class PersonalInfoDataSource @Inject constructor(
    private val api: API,
    private val db: EmployerDB
) {



}