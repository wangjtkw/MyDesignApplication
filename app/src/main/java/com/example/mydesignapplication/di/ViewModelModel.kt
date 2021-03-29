package com.example.mydesignapplication.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mydesignapplication.common.MyViewModelFactory
import com.example.mydesignapplication.ui.companyInfo.CompanyInformationViewModel
import com.example.mydesignapplication.ui.login.LoginViewModel
import com.example.mydesignapplication.ui.login.RegisterViewModel
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
    abstract fun bindViewModelFactory(factory: MyViewModelFactory): ViewModelProvider.Factory

}