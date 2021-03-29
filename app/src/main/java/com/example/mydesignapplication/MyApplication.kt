package com.example.mydesignapplication

import android.app.Application
import com.example.mydesignapplication.di.AppInjector
import com.example.mydesignapplication.utils.AppHelper
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        AppHelper.init(this)
    }

    override fun androidInjector() = dispatchingAndroidInjector
}