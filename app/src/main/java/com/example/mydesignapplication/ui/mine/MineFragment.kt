package com.example.mydesignapplication.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.mydesignapplication.ui.MainActivity
import com.example.mydesignapplication.R
import com.example.mydesignapplication.data.bean.Status
import com.example.mydesignapplication.databinding.FragmentMineBinding
import com.example.mydesignapplication.ext.isConnectedNetwork
import com.example.mydesignapplication.ui.companyInfo.CompanyInformationActivity
import com.example.mydesignapplication.ui.persionalinfo.PersonalInfoActivity
import com.example.mydesignapplication.utils.ToastUtil
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MineFragment : Fragment() {
    private lateinit var nameTextView: TextView
    private lateinit var identityTextView: TextView
    private lateinit var headImageView: ImageView
    private lateinit var personalInfoLayout: FrameLayout
    private lateinit var companyInfoLayout: FrameLayout
    private lateinit var settingLayout: FrameLayout

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mBinding: FragmentMineBinding? = null

    private val videoViewModel: MineViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_mine,
            container,
            false,
//            dataBindingComponent
        )
        init()
        return mBinding?.root
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        if (requireContext().isConnectedNetwork() && MainActivity.EMPLOYER_ACCOUNT_BEAN != null && MainActivity.EMPLOYER_ACCOUNT_BEAN!!.employerPersonalInfoId != null) {
            videoViewModel.getPersonalInfo(MainActivity.EMPLOYER_ACCOUNT_BEAN!!.employerPersonalInfoId!!)
                .observe(this) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            mBinding!!.personalInfo = it.data
                        }
                    }
                }
        } else {
            ToastUtil.makeToast("当前网络未连接！")
        }

    }

    fun init() {

        initListener()
    }

    private fun initListener() {
        mBinding!!.fragmentMineCompanyInfoLayout.setOnClickListener {
            CompanyInformationActivity.actionStart(
                requireContext(),
                MainActivity.EMPLOYER_ACCOUNT_BEAN!!
            )
        }
        mBinding!!.fragmentMinePersonalInfoLayout.setOnClickListener {
            PersonalInfoActivity.actionStart(
                requireContext(),
                MainActivity.EMPLOYER_ACCOUNT_BEAN!!
            )
        }
    }

}