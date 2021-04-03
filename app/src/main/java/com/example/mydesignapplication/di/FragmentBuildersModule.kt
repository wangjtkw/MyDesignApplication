package com.example.mydesignapplication.di

import com.example.mydesignapplication.ui.mine.MineFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeConcernFragment(): MineFragment

}