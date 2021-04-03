package com.example.mydesignapplication.ui.releasejob.releasejob4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mydesignapplication.R
import com.example.mydesignapplication.data.bean.PositionTempBean
import com.example.mydesignapplication.databinding.ActivityReleaseJob4Binding
import com.example.mydesignapplication.ui.MainActivity
import com.example.mydesignapplication.utils.ToastUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class ReleaseJob4Activity : AppCompatActivity(), HasAndroidInjector {
    private lateinit var backLayout: FrameLayout
    private lateinit var industryLayout: LinearLayout
    private lateinit var industryTextView: TextView
    private lateinit var regionLayout: LinearLayout
    private lateinit var regionTextView: TextView
    private lateinit var releaseButton: Button

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private var mBinding: ActivityReleaseJob4Binding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: ReleaseJob4ViewModel by viewModels { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_release_job4)
        init()
        observeData()
        setListener()
    }

    private fun setListener() {
        mBinding!!.activityReleaseJob4ReleaseButton.setOnClickListener {
            if (check()) {
                viewModel.releaseJob(MainActivity.EMPLOYER_ACCOUNT_BEAN!!.employerAccountId) {
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun check(): Boolean {
        val industry = mBinding!!.activityReleaseJob4IndustryTextView.text.toString()
        val city = mBinding!!.activityReleaseJob4RegionTextView.text.toString()
        if (industry == "请选择") {
            ToastUtil.makeToast("请选择所在行业！")
            return false
        }
        if (city == "请选择") {
            ToastUtil.makeToast("请选择所在地！")
            return false
        }
        PositionTempBean.apply {
            employerPositionIndustry.value = industry
            employerPositionCity.value = city
        }
        return true
    }

    private fun observeData() {
        PositionTempBean.apply {
            employerPositionCity.observe(this@ReleaseJob4Activity) {
                mBinding!!.employerPositionCity = it
            }
            employerPositionIndustry.observe(this@ReleaseJob4Activity) {
                mBinding!!.employerPositionIndustry = it
            }
        }
        viewModel.releaseJobResult.observe(this) {

        }
    }

    private fun init() {
        backLayout = f(R.id.activity_release_job4_back_layout)
        industryLayout = f(R.id.activity_release_job4_industry_layout)
        industryTextView = f(R.id.activity_release_job4_industry_text_view)
        regionLayout = f(R.id.activity_release_job4_region_layout)
        regionTextView = f(R.id.activity_release_job4_region_text_view)
        releaseButton = f(R.id.activity_release_job4_release_button)
    }

    private fun <T : View> f(id: Int): T {
        return findViewById(id)
    }
}