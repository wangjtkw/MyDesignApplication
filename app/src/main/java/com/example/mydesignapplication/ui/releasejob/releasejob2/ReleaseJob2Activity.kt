package com.example.mydesignapplication.ui.releasejob.releasejob2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mydesignapplication.R
import com.example.mydesignapplication.data.bean.PersonalInfoBean
import com.example.mydesignapplication.data.bean.PositionTempBean
import com.example.mydesignapplication.databinding.ActivityReleaseJob2Binding
import com.example.mydesignapplication.databinding.ActivityReleaseJobBinding
import com.example.mydesignapplication.dialog.SingleDataDialogUtil
import com.example.mydesignapplication.dialog.WelfareDialogUtil
import com.example.mydesignapplication.ui.releasejob.releasejob3.ReleaseJob3Activity
import com.example.mydesignapplication.utils.ToastUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class ReleaseJob2Activity : AppCompatActivity(), HasAndroidInjector {
    private lateinit var backLayout: FrameLayout
    private lateinit var salaryEditText: EditText
    private lateinit var salaryUnitLayout: LinearLayout
    private lateinit var salaryUnitTextView: TextView
    private lateinit var settlementMethodRecyclerView: RecyclerView
    private lateinit var welfareLayout: LinearLayout
    private lateinit var welfareRecyclerView: RecyclerView
    private lateinit var nextStepButton: Button

    private val salaryUnitList = ArrayList<String>()
    private lateinit var salaryUnitCallback: ((Int) -> Unit)
    private val settlementList = ArrayList<String>()
    private var settlementAdapter: SettlementRecyclerAdapter? = null
    private val welfareList = ArrayList<String>()
    private lateinit var welfareAdapter: WelfareRecyclerAdapter

    private lateinit var welfareCallback: ((String) -> Unit)

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private var mBinding: ActivityReleaseJob2Binding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_release_job2)

        init()
    }

    private fun init() {
        backLayout = f(R.id.activity_release_job2_back_layout)
        salaryEditText = f(R.id.activity_release_job2_salary_edit_text)
        salaryUnitLayout = f(R.id.activity_release_job2_salary_unit_layout)
        salaryUnitTextView = f(R.id.activity_release_job2_salary_unit_text_view)
        settlementMethodRecyclerView = f(R.id.activity_release_job2_settlement_recycler_view)
        welfareLayout = f(R.id.activity_release_job2_welfare_layout)
        welfareRecyclerView = f(R.id.activity_release_job2_welfare_recycler_view)
        nextStepButton = f(R.id.activity_release_job2_next_step_button)
        initData()
        observeData()
        setListener()
        initListener()

        initSettlementRecycler()
        initWelfareRecycler()
    }

    private fun observeData() {
        PositionTempBean.apply {
            employerPositionSalary.observe(this@ReleaseJob2Activity) {
                mBinding!!.employerPositionSalary = it
            }
            employerPositionSalaryUnit.observe(this@ReleaseJob2Activity) {
                mBinding!!.activityReleaseJob2SalaryUnitTextView.text =
                    salaryUnitList[employerPositionSalaryUnitIndex]
            }
            employerPositionSettlementIndex.observe(this@ReleaseJob2Activity) {
                if (settlementAdapter != null) {
                    settlementAdapter!!.setIndex(it)
                }
            }

        }
    }

    private fun initData() {
        salaryUnitList.apply {
            add("天")
            add("周")
            add("月")
            add("次")
            add("其他")
        }
        settlementList.apply {
            add("日结")
            add("周结")
            add("月结")
            add("完工结")
            add("其他方式")
        }
        welfareList.apply {
            add("交通补助")
            add("有提成")
            add("包吃")
            add("包住")
            add("五险")
        }
    }

    private fun initListener() {
        mBinding!!.activityReleaseJob2BackLayout.setOnClickListener {
            finish()
        }
        nextStepButton.setOnClickListener {
            if (check()) {
                val intent = Intent(this, ReleaseJob3Activity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun check(): Boolean {
        val salary = mBinding!!.activityReleaseJob2SalaryEditText.text.toString()
        val salaryUnit = mBinding!!.activityReleaseJob2SalaryUnitTextView.text.toString()
        val currentIndex = settlementAdapter?.getIndex() ?: 3
        if (salary.isEmpty()) {
            ToastUtil.makeToast("请输入薪水！")
            return false
        }
        PositionTempBean.apply {
            employerPositionSalary.value = salary
            employerPositionSalaryUnit.value = salaryUnit
            employerPositionSettlement.value = settlementList[currentIndex]
            employerPositionSettlementIndex.value = currentIndex

        }
        return true
    }


    private fun setListener() {
        salaryUnitCallback = {
            salaryUnitTextView.text = salaryUnitList[it]
            PositionTempBean.employerPositionSalaryUnitIndex = it
        }
        salaryUnitLayout.setOnClickListener {
            SingleDataDialogUtil().init(
                this,
                salaryUnitList,
                salaryUnitCallback,
                PositionTempBean.employerPositionSalaryUnitIndex
            )
        }
        welfareCallback = {
            welfareList.add(it)
            welfareAdapter.addData(it)
        }
        welfareLayout.setOnClickListener {
            WelfareDialogUtil().init(this, welfareCallback)
        }
    }

    private fun initSettlementRecycler() {
        settlementAdapter = SettlementRecyclerAdapter()
        settlementAdapter!!.addData(settlementList)
        settlementMethodRecyclerView.adapter = settlementAdapter
        settlementMethodRecyclerView.layoutManager = GridLayoutManager(this, 3)
    }

    private fun initWelfareRecycler() {
        welfareAdapter = WelfareRecyclerAdapter()
        welfareAdapter.addDataList(welfareList).setInitialSize(welfareList.size - 1)
        welfareRecyclerView.adapter = welfareAdapter
        welfareRecyclerView.layoutManager = GridLayoutManager(this, 3)
    }

    private fun <T : View> f(id: Int): T {
        return findViewById(id)
    }
}