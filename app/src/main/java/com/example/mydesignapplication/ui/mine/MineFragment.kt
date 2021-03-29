package com.example.mydesignapplication.ui.mine

import android.content.Intent
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.mydesignapplication.MainActivity
import com.example.mydesignapplication.base.BaseFragment
import com.example.mydesignapplication.R
import com.example.mydesignapplication.databinding.FragmentMineBinding
import com.example.mydesignapplication.ui.companyInfo.CompanyInformationActivity
import com.example.mydesignapplication.ui.persionalinfo.PersonalInfoActivity

class MineFragment : BaseFragment<FragmentMineBinding>() {
    private lateinit var nameTextView: TextView
    private lateinit var identityTextView: TextView
    private lateinit var headImageView: ImageView
    private lateinit var personalInfoLayout: FrameLayout
    private lateinit var companyInfoLayout: FrameLayout
    private lateinit var settingLayout: FrameLayout

    override fun init() {
        nameTextView = f(R.id.fragment_mine_name_text_view)
        identityTextView = f(R.id.fragment_mine_identity_text_view)
        headImageView = f(R.id.fragment_mine_head_image_view)
        personalInfoLayout = f(R.id.fragment_mine_personal_info_layout)
        companyInfoLayout = f(R.id.fragment_mine_company_info_layout)
        settingLayout = f(R.id.fragment_mine_setting_layout)
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

    override fun getLayoutID() = R.layout.fragment_mine
}