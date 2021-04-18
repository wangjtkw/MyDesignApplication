package com.example.mydesignapplication.ui.candidate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mydesignapplication.base.BaseFragment
import com.example.mydesignapplication.R
import com.example.mydesignapplication.databinding.FragmentCandidateBinding
import com.example.mydesignapplication.databinding.FragmentMineBinding
import com.example.mydesignapplication.publicclass.ViewPagerAdapter
import com.example.mydesignapplication.ui.MainActivity
import com.example.mydesignapplication.ui.mine.MineViewModel
import com.example.mydesignapplication.utils.TabPagerBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CandidateFragment : Fragment() {
    private lateinit var searchLayout: FrameLayout
    private lateinit var releaseJobLayout: FrameLayout
    private lateinit var releaseJobButton: Button
    private lateinit var recyclerView: RecyclerView

    private var viewPagerAdapter: ViewPagerAdapter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mBinding: FragmentCandidateBinding? = null

    private val viewModel: CandidateViewModel by viewModels { viewModelFactory }

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
            R.layout.fragment_candidate,
            container,
            false
        )
        init()
        return mBinding?.root
    }

    fun observeData() {
        viewModel.getAllRecordResult.observe(requireActivity(), {})
        viewModel.isVisibleList.observe(requireActivity()) {
            mBinding!!.isVisibleList = it
        }
    }

    fun getData() {
        viewModel.getAllRecord(MainActivity.EMPLOYER_ACCOUNT_BEAN!!.employerAccountId)
    }

    fun init() {
        searchLayout = f(R.id.fragment_candidate_search_layout)
        releaseJobLayout = f(R.id.fragment_candidate_release_job_layout)
        releaseJobButton = f(R.id.layout_candidate_release_button)
        initListener()
        observeData()
        getData()
        initViewPager()
        initTabLayout()
        TabPagerBinding.bindTabPager(
            mBinding!!.fragmentCandidateTabLayout,
            mBinding!!.fragmentCandidateViewPager
        )
    }

    private fun initViewPager() {
        val fragmentList = listOf(
            CandidatePageFragment.newInstance(""),
            CandidatePageFragment.newInstance("待结算"),
            CandidatePageFragment.newInstance("待录取"),
        )
        viewPagerAdapter = ViewPagerAdapter(fragmentList, requireActivity())
        mBinding!!.fragmentCandidateViewPager.apply {
            adapter = viewPagerAdapter
            offscreenPageLimit = 3
            currentItem = 0
        }
    }

    private fun initTabLayout() {
        mBinding!!.fragmentCandidateTabLayout.apply {
            addTab(newTab().setText("全部"))
            addTab(newTab().setText("待结算"))
            addTab(newTab().setText("待录取"))
        }
    }

    private fun initListener() {
        releaseJobButton.setOnClickListener {
            Toast.makeText(requireContext(), "click", Toast.LENGTH_SHORT).show()
        }
    }

    private fun <V> f(id: Int): V {
        return mBinding!!.root.findViewById(id)
    }


}