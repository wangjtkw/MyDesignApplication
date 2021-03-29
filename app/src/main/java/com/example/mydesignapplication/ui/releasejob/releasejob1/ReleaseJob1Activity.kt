package com.example.mydesignapplication.ui.releasejob.releasejob1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.mydesignapplication.R
import com.example.mydesignapplication.ui.releasejob.releasejob2.ReleaseJob2Activity
import com.example.mydesignapplication.utils.showSoftInputFromWindow


class ReleaseJob1Activity : AppCompatActivity() {
    private lateinit var backLayout: FrameLayout
    private lateinit var positionTitleLayout: FrameLayout
    private lateinit var positionEditText: EditText
    private lateinit var clearLayout: LinearLayout
    private lateinit var contentLayout: FrameLayout
    private lateinit var contentEditText: EditText
    private lateinit var personNumberEditText: EditText
    private lateinit var nextStepButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_release_job)
        init()
        initListener()
    }

    private fun init() {
        backLayout = f(R.id.activity_release_job_back_layout)
        positionTitleLayout = f(R.id.activity_release_job_position_title_layout)
        positionEditText = f(R.id.activity_release_job_position_title_edit_text)
        clearLayout = f(R.id.activity_release_job_content_clear_layout)
        contentLayout = f(R.id.activity_release_job_content_layout)
        contentEditText = f(R.id.activity_release_job_content_edit_text)
        personNumberEditText = f(R.id.activity_release_job_person_number_edit_text)
        nextStepButton = f(R.id.activity_release_job_next_step_button)
    }

    private fun initListener() {
        backLayout.setOnClickListener {
            finish()
        }
        positionTitleLayout.setOnClickListener {
            if (!positionEditText.isFocused) {
                showSoftInputFromWindow(this, positionEditText)
            }
        }
        clearLayout.setOnClickListener {
            contentEditText.setText("")
        }
        contentLayout.setOnClickListener {
            if (!contentEditText.isFocused) {
                showSoftInputFromWindow(this, contentEditText)
            }
        }
        nextStepButton.setOnClickListener {
            val intent = Intent(this, ReleaseJob2Activity::class.java)
            startActivity(intent)
        }
    }


    private fun <T : View> f(id: Int): T {
        return findViewById(id)
    }
}