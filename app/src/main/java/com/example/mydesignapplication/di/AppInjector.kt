package com.example.mydesignapplication.di

import com.example.mydesignapplication.MyApplication

object AppInjector {

    fun init(application: MyApplication) {
        DaggerAppComponent.builder().application(application).build().inject(application)
    }
}