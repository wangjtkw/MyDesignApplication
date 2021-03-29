package com.example.mydesignapplication.ui.releasejob.releasejob4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mydesignapplication.R

class ReleaseJob4Activity : AppCompatActivity() {
    private lateinit var backLayout: FrameLayout
    private lateinit var industryLayout: LinearLayout
    private lateinit var industryTextView: TextView
    private lateinit var regionLayout: LinearLayout
    private lateinit var regionTextView: TextView
    private lateinit var releaseButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_release_job4)
        init()
    }

    private fun init() {
        backLayout = f(R.id.activity_release_job4_back_layout)
        industryLayout = f(R.id.activity_release_job4_industry_layout)
        industryTextView = f(R.id.activity_release_job4_industry_text_view)
        regionLayout = f(R.id.activity_release_job4_region_layout)
        regionTextView = f(R.id.activity_release_job4_region_text_view)
        releaseButton = f(R.id.activity_release_job4_release_button)
    }

    private fun <T : View> f(id: Int): T {
        return findViewById(id)
    }
}