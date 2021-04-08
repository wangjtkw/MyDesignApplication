package com.example.mydesignapplication.ui.companyInfo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mydesignapplication.ui.MainActivity
import com.example.mydesignapplication.R
import com.example.mydesignapplication.data.bean.CompanyInfoBean
import com.example.mydesignapplication.data.bean.EmployerAccountBean
import com.example.mydesignapplication.data.bean.Status
import com.example.mydesignapplication.databinding.ActivityCompanyInformationBinding
import com.example.mydesignapplication.dialog.ChooseDateDialogUtil
import com.example.mydesignapplication.dialog.SingleDataDialogUtil
import com.example.mydesignapplication.ext.isConnectedNetwork
import com.example.mydesignapplication.utils.ToastUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class CompanyInformationActivity : AppCompatActivity(), HasAndroidInjector {

    private val TAG = "CompanyInformationActivity"
    private val companyTypeList = ArrayList<String>()
    private var companyTypeCallback: (Int) -> Unit = {}
    private val businessStateList = ArrayList<String>()
    private var businessStateCallback: (Int) -> Unit = {}
    private var foundTimeCallback: (String) -> Unit = {}

    private var insertCompanyInfoCallback: (EmployerAccountBean) -> Unit = {}
    private var updateCompanyInfoCallback: () -> Unit = {}

    private var mEmployerAccountBean: EmployerAccountBean? = null
    private var mCompanyInfoBean: CompanyInfoBean? = null

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val companyInformationViewModel: CompanyInformationViewModel by viewModels { viewModelFactory }

    private var mBinding: ActivityCompanyInformationBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_company_information)
        getParam()
        initData()
        observeData()
        setListener()
        initListener()

    }

    private fun getParam() {
        mEmployerAccountBean = intent.getParcelableExtra(PARAM_EMPLOYER_ACCOUNT_BEAN)
        if (mEmployerAccountBean == null) {
            ToastUtil.makeToast("传入参数错误！")
            finish()
        }
    }

    private fun initData() {
        companyTypeList.apply {
            add("合资企业")
            add("独资企业")
            add("国有企业")
            add("私营企业")
            add("全民所有制企业")
            add("集体所有制企业")
            add("股份制企业")
            add("有限责任企业")
        }
        businessStateList.apply {
            add("存续")
            add("在业")
            add("吊销")
            add("注销")
            add("迁入")
            add("迁出")
            add("停业")
            add("清算")
        }
        var companyInfoId: Int?
        if ((mEmployerAccountBean!!.employerCompanyInfo.also { companyInfoId = it }) != null) {
            companyInformationViewModel.getCompanyInfo(companyInfoId!!).observe(this) {
                when (it.status) {
                    Status.SUCCESS -> {
                        mCompanyInfoBean = it.data
                        mBinding!!.companyInfoBean = it.data
                    }
                }
            }
        }

    }

    private fun observeData() {
        companyInformationViewModel.insertCompanyInfoResult.observe(this) {

        }
        companyInformationViewModel.updateCompanyInfoResult.observe(this) {

        }
    }

    private fun setListener() {
        companyTypeCallback = {
            mBinding!!.activityCompanyInfoCompanyTypeTextView.text = companyTypeList[it]
        }
        businessStateCallback = {
            mBinding!!.activityCompanyInfoBusinessStateTextView.text = businessStateList[it]
        }
        foundTimeCallback = {
            mBinding!!.activityCompanyInfoFoundTimeTextView.text = it
        }

        insertCompanyInfoCallback = {
            MainActivity.actionStart(this, it)
            finish()
        }
        updateCompanyInfoCallback = {
            finish()
        }
    }

    private fun initListener() {
        mBinding!!.apply {
            activityCompanyInfoBackLayout.setOnClickListener {
                finish()
            }
            /**
             * 保存未做
             */
            activityCompanyInfoSaveButton.setOnClickListener {
                if (isConnectedNetwork()) {
                    if (mEmployerAccountBean!!.employerCompanyInfo == null) {
                        insertCompanyInfo()
                    } else {
                        updateCompanyInfo()
                    }
                } else {
                    ToastUtil.makeToast("当前网络未连接！")
                }
            }
            activityCompanyInfoCompanyTypeLayout.setOnClickListener {
                SingleDataDialogUtil().init(
                    this@CompanyInformationActivity,
                    companyTypeList,
                    companyTypeCallback
                )
            }
            activityCompanyInfoBusinessStateLayout.setOnClickListener {
                SingleDataDialogUtil().init(
                    this@CompanyInformationActivity,
                    businessStateList,
                    businessStateCallback
                )
            }
            activityCompanyInfoFoundTimeLayout.setOnClickListener {
                ChooseDateDialogUtil().init(this@CompanyInformationActivity, foundTimeCallback)
            }
        }
    }

    private fun insertCompanyInfo() {
        mBinding!!.apply {
            val accountId = mEmployerAccountBean!!.employerAccountId
            val companyName = activityCompanyInfoNameEditText.text.toString()
            val companyType = activityCompanyInfoCompanyTypeTextView.text.toString()
            val businessState = activityCompanyInfoBusinessStateTextView.text.toString()
            val foundTime = activityCompanyInfoFoundTimeTextView.text.toString()
            val registerAddress = activityCompanyInfoRegisterAddressEditText.text.toString()
            val uniformSocialCreditCode =
                activityCompanyInfoUniformSocialCreditCodeEditText.text.toString()
            val organizationCode = activityCompanyInfoOrganizationCodeEditText.text.toString()
            val businessScope = activityCompanyInfoBusinessScopeEditText.text.toString()
            companyInformationViewModel.insertCompanyInfo(
                accountId = accountId,
                companyName = companyName,
                companyType = companyType,
                businessState = businessState,
                foundTime = foundTime,
                registerAddress = registerAddress,
                uniformSocialCreditCode = uniformSocialCreditCode,
                organizationCode = organizationCode,
                businessScope = businessScope,
                insertCompanyInfoCallback
            )
        }
    }

    private fun updateCompanyInfo() {
        if (mCompanyInfoBean == null) {
            ToastUtil.makeToast("数据加载中...")
            return
        }
        mBinding!!.apply {
            val companyInfoId = mCompanyInfoBean!!.employerCompanyInfoId
            val companyName = activityCompanyInfoNameEditText.text.toString()
            val companyType = activityCompanyInfoCompanyTypeTextView.text.toString()
            val businessState = activityCompanyInfoBusinessStateTextView.text.toString()
            val foundTime = activityCompanyInfoFoundTimeTextView.text.toString()
            val registerAddress = activityCompanyInfoRegisterAddressEditText.text.toString()
            val uniformSocialCreditCode =
                activityCompanyInfoUniformSocialCreditCodeEditText.text.toString()
            val organizationCode = activityCompanyInfoOrganizationCodeEditText.text.toString()
            val businessScope = activityCompanyInfoBusinessScopeEditText.text.toString()
            companyInformationViewModel.updateCompanyInfo(
                companyInfoId = companyInfoId,
                companyName = companyName,
                companyType = companyType,
                businessState = businessState,
                foundTime = foundTime,
                registerAddress = registerAddress,
                uniformSocialCreditCode = uniformSocialCreditCode,
                organizationCode = organizationCode,
                businessScope = businessScope,
                updateCompanyInfoCallback
            )
        }
    }

    companion object {
        const val PARAM_EMPLOYER_ACCOUNT_BEAN = "employer_account_bean"

        fun actionStart(context: Context, employerAccountBean: EmployerAccountBean) {
            val intent = Intent(context, CompanyInformationActivity::class.java)
            intent.putExtra(PARAM_EMPLOYER_ACCOUNT_BEAN, employerAccountBean)
            context.startActivity(intent)
        }
    }
}