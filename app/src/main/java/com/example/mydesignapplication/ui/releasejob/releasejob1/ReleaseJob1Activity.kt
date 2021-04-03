package com.example.mydesignapplication.ui.releasejob.releasejob1

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mydesignapplication.R
import com.example.mydesignapplication.base.PermissionActivity
import com.example.mydesignapplication.data.bean.PositionTempBean
import com.example.mydesignapplication.databinding.ActivityReleaseJobBinding
import com.example.mydesignapplication.ui.album.AlbumActivity
import com.example.mydesignapplication.ui.album.AlbumBean
import com.example.mydesignapplication.ui.login.LoginViewModel
import com.example.mydesignapplication.ui.persionalinfo.PersonalInfoActivity
import com.example.mydesignapplication.ui.releasejob.releasejob2.ReleaseJob2Activity
import com.example.mydesignapplication.utils.ToastUtil
import com.example.mydesignapplication.utils.showSoftInputFromWindow
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class ReleaseJob1Activity : PermissionActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mBinding: ActivityReleaseJobBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_release_job)
        observeData()
        initListener()
    }

    private fun observeData() {
        PositionTempBean.apply {
            employerPositionTitle.observe(this@ReleaseJob1Activity) {
                mBinding!!.employerPositionTitle = it
            }
            employerPositionContent.observe(this@ReleaseJob1Activity) {
                mBinding!!.employerPositionContent = it
            }
            employerPositionPersonNum.observe(this@ReleaseJob1Activity) {
                mBinding!!.employerPositionPersonNum = it
            }
            employerPositionImg.observe(this@ReleaseJob1Activity) {
                val uri = Uri.parse(it.DATA)
                mBinding!!.activityPersonalCardFrontImg.setImageURI(uri)
            }
        }
    }

    private fun initListener() {
        mBinding!!.activityReleaseJobBackLayout.setOnClickListener {
            finish()
        }
        mBinding!!.activityReleaseJobPositionTitleLayout.setOnClickListener {
            if (!mBinding!!.activityReleaseJobPositionTitleEditText.isFocused) {
                showSoftInputFromWindow(this, mBinding!!.activityReleaseJobPositionTitleEditText)
            }
        }
        mBinding!!.activityReleaseJobContentClearLayout.setOnClickListener {
            mBinding!!.activityReleaseJobContentEditText.setText("")
        }
        mBinding!!.activityReleaseJobContentLayout.setOnClickListener {
            if (!mBinding!!.activityReleaseJobContentEditText.isFocused) {
                showSoftInputFromWindow(this, mBinding!!.activityReleaseJobContentEditText)
            }
        }
        mBinding!!.activityReleaseJobNextStepButton.setOnClickListener {
            if (check()) {
                val intent = Intent(this, ReleaseJob2Activity::class.java)
                startActivity(intent)
            }
        }
        mBinding!!.activityPersonalCardFrontImg.setOnClickListener {
            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun check(): Boolean {
        val title = mBinding!!.activityReleaseJobPositionTitleEditText.text.toString()
        val content = mBinding!!.activityReleaseJobContentEditText.text.toString()
        val personNum = mBinding!!.activityReleaseJobPersonNumberEditText.text.toString()
        val imgAlbumBean = PositionTempBean.employerPositionImg.value
        if (title.isEmpty()) {
            ToastUtil.makeToast("请输入标题！")
            return false
        }
        if (content.isEmpty()) {
            ToastUtil.makeToast("请输入工作内容！")
            return false
        }
        if (personNum.isEmpty()) {
            ToastUtil.makeToast("请输入招聘人数！")
            return false
        }
        if (imgAlbumBean == null) {
            ToastUtil.makeToast("请选择岗位图片！")
            return false
        }
        PositionTempBean.apply {
            employerPositionTitle.value = title
            employerPositionContent.value = content
            employerPositionPersonNum.value = personNum
        }

        return true
    }

    override fun doOnGetPermission(permission: String, tag: Int) {
        super.doOnGetPermission(permission, tag)
        val intent = Intent(this, AlbumActivity::class.java)
        startActivityForResult(intent, AlbumActivity.ALBUM_ACTIVITY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AlbumActivity.ALBUM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val jobAlbumBean = data.getParcelableExtra<AlbumBean>(AlbumActivity.ALBUM_BEAN)
            PositionTempBean.employerPositionImg.value = jobAlbumBean
            val uri = Uri.parse(jobAlbumBean!!.DATA)
            mBinding!!.activityPersonalCardFrontImg.setImageURI(uri)
        }
    }


}