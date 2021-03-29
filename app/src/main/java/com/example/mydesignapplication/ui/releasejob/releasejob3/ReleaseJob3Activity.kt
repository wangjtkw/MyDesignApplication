package com.example.mydesignapplication.ui.releasejob.releasejob3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.mydesignapplication.R
import com.example.mydesignapplication.dialog.SingleDataDialogUtil
import com.example.mydesignapplication.ui.releasejob.releasejob4.ReleaseJob4Activity

class ReleaseJob3Activity : AppCompatActivity() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_release_job3)
        init()

    }

    private fun init() {
        backLayout = f(R.id.activity_release_job3_back_layout)
        workPlaceLayout = f(R.id.activity_release_job3_work_place_layout)
        workPlaceTextView = f(R.id.activity_release_job3_work_place_text_view)
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
        initListener()
        setListener()
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
            val intent = Intent(this, ReleaseJob4Activity::class.java)
            startActivity(intent)
        }
    }

    private fun setListener() {
        workDateCallback = {
            workDateTextView.text = workDateList[it]
        }
        workDateLayout.setOnClickListener {
            SingleDataDialogUtil().init(this, workDateList, workDateCallback)

        }
        contactInformationCallback = {
            setContactInformationLayout(contactInformationList[it])
            contactInformationTextView.text = contactInformationList[it]
        }
        contactInformationLayout.setOnClickListener {
            SingleDataDialogUtil().init(this, contactInformationList, contactInformationCallback)
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