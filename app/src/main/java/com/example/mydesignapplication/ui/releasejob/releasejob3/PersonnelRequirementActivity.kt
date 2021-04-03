package com.example.mydesignapplication.ui.releasejob.releasejob3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.mydesignapplication.R
import com.example.mydesignapplication.data.bean.PositionTempBean
import com.example.mydesignapplication.databinding.ActivityPersonalInfoBinding
import com.example.mydesignapplication.databinding.ActivityReleaseJob2Binding
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class PersonnelRequirementActivity : AppCompatActivity(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private var mBinding: ActivityPersonalInfoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_personnel_requirement)

    }


}