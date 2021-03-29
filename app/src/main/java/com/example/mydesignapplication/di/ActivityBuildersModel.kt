package com.example.mydesignapplication.di

import androidx.paging.ExperimentalPagingApi
import com.example.mydesignapplication.MainActivity
import com.example.mydesignapplication.ui.companyInfo.CompanyInformationActivity
import com.example.mydesignapplication.ui.login.LoginActivity
import com.example.mydesignapplication.ui.login.RegisterActivity
import com.example.mydesignapplication.ui.persionalinfo.PersonalInfoActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModel {

//    @ExperimentalPagingApi
//    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
//    abstract fun contributeMainActivity(): MainActivity

    @ExperimentalPagingApi
    @ContributesAndroidInjector()
    abstract fun contributeRegisterActivity(): RegisterActivity

    @ExperimentalPagingApi
    @ContributesAndroidInjector()
    abstract fun contributeLoginActivity(): LoginActivity

    @ExperimentalPagingApi
    @ContributesAndroidInjector()
    abstract fun contributeCompanyInformationActivity(): CompanyInformationActivity

    @ExperimentalPagingApi
    @ContributesAndroidInjector()
    abstract fun contributePersonalInfoActivity(): PersonalInfoActivity

}