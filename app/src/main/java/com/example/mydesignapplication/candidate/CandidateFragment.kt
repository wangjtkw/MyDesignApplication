package com.example.mydesignapplication.candidate

import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mydesignapplication.base.BaseFragment
import com.example.mydesignapplication.R

class CandidateFragment : BaseFragment() {
    private lateinit var searchLayout: FrameLayout
    private lateinit var releaseJobLayout: FrameLayout
    private lateinit var releaseJobButton: Button
    private lateinit var recyclerView: RecyclerView

    override fun init() {
        searchLayout = f(R.id.fragment_candidate_search_layout)
        releaseJobLayout = f(R.id.fragment_candidate_release_job_layout)
        releaseJobButton = f(R.id.layout_candidate_release_button)
        recyclerView = f(R.id.fragment_candidate_recycler_view)
        initListener()
    }

    private fun initListener() {
        releaseJobButton.setOnClickListener {
            Toast.makeText(requireContext(), "click", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getLayoutID() = R.layout.fragment_candidate


}