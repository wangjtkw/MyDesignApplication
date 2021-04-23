package com.example.mydesignapplication.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mydesignapplication.common.MyViewModelFactory
import com.example.mydesignapplication.ui.candidate.CandidatePageViewModel
import com.example.mydesignapplication.ui.candidate.CandidateViewModel
import com.example.mydesignapplication.ui.chatting.ChattingViewModel
import com.example.mydesignapplication.ui.companyInfo.CompanyInformationViewModel
import com.example.mydesignapplication.ui.login.LoginViewModel
import com.example.mydesignapplication.ui.login.RegisterViewModel
import com.example.mydesignapplication.ui.message.MessageViewModel
import com.example.mydesignapplication.ui.mine.MineViewModel
import com.example.mydesignapplication.ui.persionalinfo.PersonalInfoViewModel
import com.example.mydesignapplication.ui.post.PostPageViewModel
import com.example.mydesignapplication.ui.releasejob.releasejob4.ReleaseJob4ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModel {

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CompanyInformationViewModel::class)
    abstract fun bindCompanyInformationViewModel(companyInformationViewModel: CompanyInformationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PersonalInfoViewModel::class)
    abstract fun bindPersonalInfoViewModel(personalInfoViewModel: PersonalInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MineViewModel::class)
    abstract fun bindMineViewModel(mineViewModel: MineViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReleaseJob4ViewModel::class)
    abstract fun bindReleaseJob4ViewModel(releaseJob1ViewModel: ReleaseJob4ViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostPageViewModel::class)
    abstract fun bindPostPageViewModel(postPageViewModel: PostPageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CandidateViewModel::class)
    abstract fun bindCandidateViewModel(candidateViewModel: CandidateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CandidatePageViewModel::class)
    abstract fun bindCandidatePageViewModel(candidatePageViewModel: CandidatePageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MessageViewModel::class)
    abstract fun bindMessageViewModel(messageViewModel: MessageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChattingViewModel::class)
    abstract fun bindChattingViewModel(chattingViewModel: ChattingViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: MyViewModelFactory): ViewModelProvider.Factory

}