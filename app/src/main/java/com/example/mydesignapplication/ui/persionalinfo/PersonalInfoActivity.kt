package com.example.mydesignapplication.ui.persionalinfo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mydesignapplication.ui.album.AlbumActivity
import com.example.mydesignapplication.R
import com.example.mydesignapplication.base.PermissionActivity
import com.example.mydesignapplication.data.bean.EmployerAccountBean
import com.example.mydesignapplication.data.bean.PersonalInfoBean
import com.example.mydesignapplication.data.bean.Status
import com.example.mydesignapplication.databinding.ActivityLoginBinding
import com.example.mydesignapplication.databinding.ActivityPersonalInfoBinding
import com.example.mydesignapplication.ext.isConnectedNetwork
import com.example.mydesignapplication.ui.album.AlbumActivity.Companion.ALBUM_ACTIVITY_REQUEST_CODE
import com.example.mydesignapplication.ui.album.AlbumActivity.Companion.ALBUM_BEAN
import com.example.mydesignapplication.ui.album.AlbumBean
import com.example.mydesignapplication.ui.companyInfo.CompanyInformationActivity
import com.example.mydesignapplication.ui.login.LoginViewModel
import com.example.mydesignapplication.utils.ToastUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class PersonalInfoActivity : PermissionActivity(), HasAndroidInjector {

    private val TAG = "PersonalInfoActivity"

    private var mEmployerAccountBean: EmployerAccountBean? = null

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val personalViewModel: PersonalInfoViewModel by viewModels { viewModelFactory }

    private var mBinding: ActivityPersonalInfoBinding? = null

    private var headImgAlbumBean: AlbumBean? = null
    private var frontImgAlbumBean: AlbumBean? = null
    private var backImgAlbumBean: AlbumBean? = null

    private var insertPersonalInfoCallback: () -> Unit = {}
    private var updatePersonalInfoCallback: () -> Unit = {}
    private var mPersonalInfoBean: PersonalInfoBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_personal_info)
        init()
    }

    private fun init() {
        initParam()
        observeData()
        setListener()
        initListener()

    }

    private fun initListener() {
        mBinding!!.activityPersonalHeadImgImg.setOnClickListener {
            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, HEAD_IMG)
        }
        mBinding!!.activityPersonalCardFrontImg.setOnClickListener {
            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, FRONT_IMG)
        }
        mBinding!!.activityPersonalCardBackImg.setOnClickListener {
            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, BACK_IMG)
        }
        mBinding!!.activityPersonalSaveBt.setOnClickListener {
            if (isConnectedNetwork()) {
                if (mEmployerAccountBean!!.employerPersonalInfoId == null) {
                    insertPersonalInfo()
                } else {
                    updatePersonalInfo()
                }
            } else {
                ToastUtil.makeToast("当前网络未连接！")
            }

        }

    }

    private fun observeData() {
        personalViewModel.insertPersonalInfoResult.observe(this) {

        }
        personalViewModel.updatePersonalInfoResult.observe(this) {

        }
        var personalInfoId: Int?
        if (mEmployerAccountBean!!.employerPersonalInfoId.also { personalInfoId = it } != null) {
            personalViewModel.getPersonalInfo(personalInfoId!!).observe(this) {
                when (it.status) {
                    Status.SUCCESS -> {
                        mPersonalInfoBean = it.data
                        mBinding!!.personalInfoBean = it.data
                    }
                }
            }
        }
    }

    private fun setListener() {
        insertPersonalInfoCallback = {
            finish()
        }
        updatePersonalInfoCallback = {
            finish()
        }
    }

    private fun insertPersonalInfo() {
        mBinding!!.apply {
            val accountId = mEmployerAccountBean!!.employerAccountId
            val name = activityPersonalInfoNameEditText.text.toString()
            val idNum = activityPersonalInfoIDNOEditText.text.toString()
            personalViewModel.insertPersonalInfo(
                accountId = accountId,
                headImg = headImgAlbumBean,
                frontImg = frontImgAlbumBean,
                backImg = backImgAlbumBean,
                name = name,
                idNum = idNum,
                callback = insertPersonalInfoCallback
            )
        }
    }

    private fun initParam() {
        mEmployerAccountBean = intent.getParcelableExtra(PARAM_EMPLOYER_ACCOUNT_BEAN)
        if (mEmployerAccountBean == null) {
            ToastUtil.makeToast("传入参数错误！")
            finish()
        } else {
            Log.d(TAG, mEmployerAccountBean!!.toString())
        }
    }

    override fun doOnGetPermission(permission: String, tag: Int) {
        super.doOnGetPermission(permission, tag)
        when (tag) {
            HEAD_IMG -> {
                val intent = Intent(this, AlbumActivity::class.java)
                startActivityForResult(intent, HEAD_IMG)
            }
            FRONT_IMG -> {
                val intent = Intent(this, AlbumActivity::class.java)
                startActivityForResult(intent, FRONT_IMG)
            }
            BACK_IMG -> {
                val intent = Intent(this, AlbumActivity::class.java)
                startActivityForResult(intent, BACK_IMG)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == HEAD_IMG && resultCode == RESULT_OK && data != null) {
            headImgAlbumBean = data.getParcelableExtra(ALBUM_BEAN)
            val uri = Uri.parse(headImgAlbumBean!!.DATA)
            mBinding!!.activityPersonalHeadImgImg.setImageURI(uri)
        }
        if (requestCode == FRONT_IMG && resultCode == RESULT_OK && data != null) {
            frontImgAlbumBean = data.getParcelableExtra(ALBUM_BEAN)
            val uri = Uri.parse(frontImgAlbumBean!!.DATA)
            mBinding!!.activityPersonalCardFrontImg.setImageURI(uri)
        }
        if (requestCode == BACK_IMG && resultCode == RESULT_OK && data != null) {
            backImgAlbumBean = data.getParcelableExtra(ALBUM_BEAN)
            val uri = Uri.parse(backImgAlbumBean!!.DATA)
            mBinding!!.activityPersonalCardBackImg.setImageURI(uri)
        }
    }

    private fun updatePersonalInfo() {
        if (mPersonalInfoBean == null) {
            ToastUtil.makeToast("数据加载中...")
            return
        }
        mBinding!!.apply {
            val personalInfoId = mPersonalInfoBean!!.employerPersonalInfoId
            val name = activityPersonalInfoNameEditText.text.toString()
            val idNum = activityPersonalInfoIDNOEditText.text.toString()
            personalViewModel.updatePersonalInfo(
                personalInfoId = personalInfoId,
                headImg = headImgAlbumBean,
                frontImg = frontImgAlbumBean,
                backImg = backImgAlbumBean,
                name = name,
                idNum = idNum,
                callback = updatePersonalInfoCallback
            )
        }
    }

    companion object {

        const val HEAD_IMG = 0x0001
        const val FRONT_IMG = 0x0002
        const val BACK_IMG = 0x0003

        const val PARAM_EMPLOYER_ACCOUNT_BEAN = "employer_account_bean"
        fun actionStart(context: Context, employerAccountBean: EmployerAccountBean) {
            val intent = Intent(context, PersonalInfoActivity::class.java)
            intent.putExtra(
                PARAM_EMPLOYER_ACCOUNT_BEAN,
                employerAccountBean
            )
            context.startActivity(intent)
        }
    }


}