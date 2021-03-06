package com.example.mydesignapplication.di

import androidx.paging.ExperimentalPagingApi
import com.example.mydesignapplication.ui.MainActivity
import com.example.mydesignapplication.ui.chatting.ChattingActivity
import com.example.mydesignapplication.ui.companyInfo.CompanyInformationActivity
import com.example.mydesignapplication.ui.login.LoginActivity
import com.example.mydesignapplication.ui.login.RegisterActivity
import com.example.mydesignapplication.ui.persionalinfo.PersonalInfoActivity
import com.example.mydesignapplication.ui.releasejob.releasejob1.ReleaseJob1Activity
import com.example.mydesignapplication.ui.releasejob.releasejob2.ReleaseJob2Activity
import com.example.mydesignapplication.ui.releasejob.releasejob3.PersonnelRequirementActivity
import com.example.mydesignapplication.ui.releasejob.releasejob3.ReleaseJob3Activity
import com.example.mydesignapplication.ui.releasejob.releasejob4.ReleaseJob4Activity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModel {

    @ExperimentalPagingApi
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity

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

    @ExperimentalPagingApi
    @ContributesAndroidInjector()
    abstract fun contributeReleaseJob1Activity(): ReleaseJob1Activity

    @ExperimentalPagingApi
    @ContributesAndroidInjector()
    abstract fun contributeReleaseJob2Activity(): ReleaseJob2Activity

    @ExperimentalPagingApi
    @ContributesAndroidInjector()
    abstract fun contributeReleaseJob3Activity(): ReleaseJob3Activity

    @ExperimentalPagingApi
    @ContributesAndroidInjector()
    abstract fun contributePersonnelRequirementActivity(): PersonnelRequirementActivity

    @ExperimentalPagingApi
    @ContributesAndroidInjector()
    abstract fun contributeReleaseJob4Activity(): ReleaseJob4Activity

    @ExperimentalPagingApi
    @ContributesAndroidInjector()
    abstract fun contributeChattingActivity(): ChattingActivity

}