package com.example.mydesignapplication.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.mydesignapplication.base.BaseFragment
import com.example.mydesignapplication.R
import com.example.mydesignapplication.databinding.FragmentMineBinding
import com.example.mydesignapplication.databinding.FragmentPostBinding
import com.example.mydesignapplication.publicclass.ViewPagerAdapter
import com.example.mydesignapplication.ui.mine.MineViewModel
import com.example.mydesignapplication.utils.TabPagerBinding
import com.google.android.material.tabs.TabLayout
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class PostFragment : Fragment() {

    private val fragmentList = ArrayList<Fragment>()
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mBinding: FragmentPostBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_post,
            container,
            false,
//            dataBindingComponent
        )
        init()
        return mBinding?.root
    }

    fun init() {
        initTabLayout()
        initViewPager()
        TabPagerBinding.bindTabPager(
            mBinding!!.fragmentCandidateTabLayout,
            mBinding!!.fragmentCandidateViewPager
        )
    }

    private fun initTabLayout() {
        mBinding!!.fragmentCandidateTabLayout.apply {
            addTab(newTab().setText("招聘中"))
            addTab(newTab().setText("待审批"))
            addTab(newTab().setText("已下线"))
        }
    }

    private fun initViewPager() {
        fragmentList.apply {
            //招聘中
            add(PostPageFragment.newInstance("招聘中"))
            //待发布
            add(PostPageFragment.newInstance("待审批"))
            //已下线
            add(PostPageFragment.newInstance("已下线"))
        }
        viewPagerAdapter = ViewPagerAdapter(fragmentList, requireActivity())
        mBinding!!.fragmentCandidateViewPager.adapter = viewPagerAdapter
        mBinding!!.fragmentCandidateViewPager.currentItem = 0
    }
}