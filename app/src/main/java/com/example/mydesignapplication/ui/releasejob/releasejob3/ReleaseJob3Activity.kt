package com.example.mydesignapplication.ui.releasejob.releasejob3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.mydesignapplication.R
import com.example.mydesignapplication.data.bean.PositionTempBean
import com.example.mydesignapplication.databinding.ActivityReleaseJob2Binding
import com.example.mydesignapplication.databinding.ActivityReleaseJob3Binding
import com.example.mydesignapplication.dialog.SingleDataDialogUtil
import com.example.mydesignapplication.ui.releasejob.releasejob4.ReleaseJob4Activity
import com.example.mydesignapplication.utils.ToastUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class ReleaseJob3Activity : AppCompatActivity(), HasAndroidInjector {
    private val TAG = "ReleaseJob3Activity"
    private lateinit var backLayout: FrameLayout
    private lateinit var workPlaceLayout: LinearLayout
    private lateinit var workPlaceTextView: TextView
    private lateinit var workDateLayout: LinearLayout
    private lateinit var workDateTextView: TextView
    private lateinit var contactInformationLayout: LinearLayout
    private lateinit var contactInformationTextView: TextView
    private lateinit var contactInformationDetailLayout: FrameLayout
    private lateinit var contactInformationDetailTextView: TextView
    private lateinit var contactInformationDetailEditText: EditText
    private lateinit var personnelRequirementLayout: LinearLayout
    private lateinit var personnelRequirementTextView: TextView
    private lateinit var nextStepButton: Button

    private val workDateList = ArrayList<String>()
    private lateinit var workDateCallback: ((Int) -> Unit)
    private val contactInformationList = ArrayList<String>()
    private lateinit var contactInformationCallback: ((Int) -> Unit)

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private var mBinding: ActivityReleaseJob3Binding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_release_job3)
        init()

    }

    private fun init() {
        backLayout = f(R.id.activity_release_job3_back_layout)
//        workPlaceLayout = f(R.id.activity_release_job3_work_place_layout)
//        workPlaceTextView = f(R.id.activity_release_job3_work_place_text_view)
        workDateLayout = f(R.id.activity_release_job3_work_date_layout)
        workDateTextView = f(R.id.activity_release_job3_work_date_text_view)
        contactInformationLayout = f(R.id.activity_release_job3_contact_information_layout)
        contactInformationTextView = f(R.id.activity_release_job3_contact_information_text_view)
        contactInformationDetailLayout =
            f(R.id.activity_release_job3_contact_information_detail_layout)
        contactInformationDetailTextView =
            f(R.id.activity_release_job3_contact_information_detail_text_view)
        contactInformationDetailEditText =
            f(R.id.activity_release_job3_contact_information_detail_edit_text)
        personnelRequirementLayout = f(R.id.activity_release_job3_personnel_requirements_layout)
        personnelRequirementTextView =
            f(R.id.activity_release_job3_personnel_requirements_text_view)
        nextStepButton = f(R.id.activity_release_job3_next_step_button)
        initData()
        observeData()
        initListener()
        setListener()
    }

    private fun observeData() {
        PositionTempBean.apply {
            employerPositionPlace.observe(this@ReleaseJob3Activity) {
                mBinding!!.employerPositionPlace = it
            }
            employerPositionDate.observe(this@ReleaseJob3Activity) {
                mBinding!!.employerPositionDate = it
            }
            employerPositionConnectType.observe(this@ReleaseJob3Activity) {
                mBinding!!.employerPositionConnectType = it
            }
            employerPositionConnectInfo.observe(this@ReleaseJob3Activity) {
                mBinding!!.employerPositionConnectInfo = it
            }
            isNeedPersonRequirement.observe(this@ReleaseJob3Activity) {
                mBinding!!.activityReleaseJob3PersonnelRequirementsTextView.text = if (it) {
                    "已填写"
                } else {
                    "不限"
                }
            }
        }
    }

    private fun initData() {
        workDateList.apply {
            add("任意日期")
            add("周末节假日")
            add("工作日")
            add("依据个人时间安排")
            add("其它")
        }
        contactInformationList.apply {
            add("微信")
            add("QQ")
            add("QQ群")
            add("微信公众号")
            add("不要主动联系我")
            add("电话联系")
        }
    }

    private fun initListener() {
        backLayout.setOnClickListener {
            finish()
        }
        nextStepButton.setOnClickListener {
            if (check()) {
                val intent = Intent(this, ReleaseJob4Activity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun check(): Boolean {
        val place = mBinding!!.activityReleaseJob3WorkPlaceText.text.toString()
        val date = mBinding!!.activityReleaseJob3WorkDateTextView.text.toString()
        val connectType = mBinding!!.activityReleaseJob3ContactInformationTextView.text.toString()
        val connectDetail =
            mBinding!!.activityReleaseJob3ContactInformationDetailEditText.text.toString()
        if (place.isEmpty()) {
            ToastUtil.makeToast("请输入工作地点！")
            return false
        }
        if (date == "请选择") {
            ToastUtil.makeToast("请选择工作日期！")
            return false
        }
        if (connectType == "请选择") {
            ToastUtil.makeToast("请选择联系方式！")
            return false
        }
        if (connectDetail.isEmpty() && connectType != "不要主动联系我") {
            ToastUtil.makeToast("请输入联系信息！")
            return false
        }
        PositionTempBean.apply {
            employerPositionPlace.value = place
            employerPositionDate.value = date
            employerPositionConnectType.value = connectType
            employerPositionConnectInfo.value = connectDetail
        }
        return true
    }

    private fun setListener() {
        workDateCallback = {
            workDateTextView.text = workDateList[it]
            PositionTempBean.employerPositionDateIndex = it
        }
        workDateLayout.setOnClickListener {
            SingleDataDialogUtil().init(
                this,
                workDateList,
                workDateCallback,
                PositionTempBean.employerPositionDateIndex
            )

        }
        contactInformationCallback = {
            setContactInformationLayout(contactInformationList[it])
            contactInformationTextView.text = contactInformationList[it]
            PositionTempBean.employerPositionConnectTypeIndex = it
        }
        contactInformationLayout.setOnClickListener {
            SingleDataDialogUtil().init(
                this,
                contactInformationList,
                contactInformationCallback,
                PositionTempBean.employerPositionConnectTypeIndex
            )
        }


    }

    private fun setContactInformationLayout(str: String) {
        when (str) {
            "不要主动联系我" -> {
                contactInformationDetailLayout.visibility = View.GONE
            }
            else -> {
                contactInformationDetailLayout.visibility = View.VISIBLE
                contactInformationDetailTextView.text = str
            }
        }
    }

    private fun <T : View> f(id: Int): T {
        return findViewById(id)
    }
}