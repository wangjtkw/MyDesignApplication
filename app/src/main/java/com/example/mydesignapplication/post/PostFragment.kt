package com.example.mydesignapplication.post

import androidx.fragment.app.Fragment
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.mydesignapplication.base.BaseFragment
import com.example.mydesignapplication.R
import com.example.mydesignapplication.publicclass.ViewPagerAdapter
import com.example.mydesignapplication.utils.TabPagerBinding
import com.google.android.material.tabs.TabLayout

class PostFragment : BaseFragment() {
    private lateinit var searchLayout: FrameLayout
    private lateinit var postAuthenticationLayout: ConstraintLayout
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    private val fragmentList = ArrayList<Fragment>()
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun init() {
        searchLayout = f(R.id.fragment_post_search_layout)
        postAuthenticationLayout = f(R.id.post_authentication_layout)
        tabLayout = f(R.id.fragment_candidate_tab_layout)
        viewPager = f(R.id.fragment_candidate_view_pager)
        initTabLayout()
        initViewPager()
        TabPagerBinding.bindTabPager(tabLayout, viewPager)
    }

    private fun initTabLayout() {
        tabLayout.apply {
            addTab(newTab().setText("招聘中"))
            addTab(newTab().setText("待发布"))
            addTab(newTab().setText("已下线"))
        }
    }

    private fun initViewPager() {
        fragmentList.apply {
            //招聘中
            add(PostPageFragment.newInstance("recruiting"))
            //待发布
            add(PostPageFragment.newInstance("releasing"))
            //已下线
            add(PostPageFragment.newInstance("offline"))
        }
        viewPagerAdapter = ViewPagerAdapter(fragmentList, requireActivity())
        viewPager.adapter = viewPagerAdapter
        viewPager.currentItem = 0
    }

    override fun getLayoutID() = R.layout.fragment_post

}