package com.example.mydesignapplication.ui.post

import android.os.Bundle
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mydesignapplication.base.BaseFragment
import com.example.mydesignapplication.R
import com.example.mydesignapplication.databinding.FragmentPostPageBinding

private const val ARG_PARAM_TYPE = "type"

class PostPageFragment : BaseFragment<FragmentPostPageBinding>() {
    private var paramType: String = ""

    private lateinit var postPageReleaseJobLayout: FrameLayout
    private lateinit var recyclerView: RecyclerView

    override fun init() {
        postPageReleaseJobLayout = f(R.id.post_page_release_job_layout)
        recyclerView = f(R.id.post_page_recycler_view)
    }

    override fun initParameters() {
        arguments?.let {
            paramType = it.getString(ARG_PARAM_TYPE) ?: ""
        }
    }

    override fun getLayoutID() = R.layout.fragment_post_page

    companion object {
        @JvmStatic
        fun newInstance(paramType: String) =
            PostPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_TYPE, paramType)
                }
            }
    }
}