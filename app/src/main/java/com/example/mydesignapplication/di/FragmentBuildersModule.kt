package com.example.mydesignapplication.di

import com.example.mydesignapplication.ui.candidate.CandidateFragment
import com.example.mydesignapplication.ui.candidate.CandidatePageFragment
import com.example.mydesignapplication.ui.message.MessageFragment
import com.example.mydesignapplication.ui.mine.MineFragment
import com.example.mydesignapplication.ui.post.PostFragment
import com.example.mydesignapplication.ui.post.PostPageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeConcernFragment(): MineFragment

    @ContributesAndroidInjector
    abstract fun contributePostFragment(): PostFragment

    @ContributesAndroidInjector
    abstract fun contributePostPageFragment(): PostPageFragment

    @ContributesAndroidInjector
    abstract fun contributeCandidateFragment(): CandidateFragment

    @ContributesAndroidInjector
    abstract fun contributeCandidatePageFragment(): CandidatePageFragment

    @ContributesAndroidInjector
    abstract fun contributeMessageFragment(): MessageFragment
}