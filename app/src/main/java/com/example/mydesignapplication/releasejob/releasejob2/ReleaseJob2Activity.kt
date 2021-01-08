package com.example.mydesignapplication.releasejob.releasejob2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mydesignapplication.R
import com.example.mydesignapplication.dialog.SingleDataDialogUtil

class ReleaseJob2Activity : AppCompatActivity() {
    private lateinit var backLayout: FrameLayout
    private lateinit var salaryEditText: EditText
    private lateinit var salaryUnitLayout: LinearLayout
    private lateinit var salaryUnitTextView: TextView
    private lateinit var settlementRecyclerView: RecyclerView
    private lateinit var welfareLayout: LinearLayout
    private lateinit var welfareRecyclerView: RecyclerView
    private lateinit var nextStepButton: Button

    private var salaryUnitList =  ArrayList<String>()
    private lateinit var salaryUnitCallback: ((Int) -> Unit)
    private val settlementList = ArrayList<String>()
    private lateinit var settlementAdapter: SettlementRecyclerAdapter
    private val welfareList = ArrayList<String>()
    private lateinit var welfareAdapter: WelfareRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_release_job2)
        init()
    }

    private fun init() {
        backLayout = f(R.id.activity_release_job2_back_layout)
        salaryEditText = f(R.id.activity_release_job2_salary_edit_text)
        salaryUnitLayout = f(R.id.activity_release_job2_salary_unit_layout)
        salaryUnitTextView = f(R.id.activity_release_job2_salary_unit_text_view)
        settlementRecyclerView = f(R.id.activity_release_job2_settlement_recycler_view)
        welfareLayout = f(R.id.activity_release_job2_welfare_layout)
        welfareRecyclerView = f(R.id.activity_release_job2_welfare_recycler_view)
        nextStepButton = f(R.id.activity_release_job2_next_step_button)
        initData()
        setListener()
        initSettlementRecycler()
        initWelfareRecycler()
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


    private fun setListener() {
        salaryUnitCallback = {
            salaryUnitTextView.text = salaryUnitList[it]
        }
        salaryUnitLayout.setOnClickListener {
            SingleDataDialogUtil().init(this, salaryUnitList, salaryUnitCallback)
        }
    }

    private fun initSettlementRecycler() {
        settlementAdapter = SettlementRecyclerAdapter()
        settlementAdapter.addData(settlementList)
        settlementRecyclerView.adapter = settlementAdapter
        settlementRecyclerView.layoutManager = GridLayoutManager(this, 3)
    }

    private fun initWelfareRecycler() {
        welfareAdapter = WelfareRecyclerAdapter()
        welfareAdapter.addData(welfareList).setInitialSize(welfareList.size)
        welfareRecyclerView.adapter = welfareAdapter
        welfareRecyclerView.layoutManager = GridLayoutManager(this, 3)
    }

    private fun <T : View> f(id: Int): T {
        return findViewById(id)
    }
}