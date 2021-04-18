package com.example.mydesignapplication.ui.candidate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydesignapplication.R
import com.example.mydesignapplication.databinding.FragmentCandidateBinding
import com.example.mydesignapplication.databinding.FragmentCandidatePageBinding
import com.example.mydesignapplication.ui.MainActivity
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CandidatePageFragment : Fragment() {
    private val TAG = "CandidatePageFragment"
    private var paramType: String? = ""

    private var recyclerAdapter: CandidatePageRecyclerAdapter? = null

    private var removeCallback: (Int) -> Unit = {}
    private var signUpCallback: (Int) -> Unit = {}
    private var settlementCallback: (Int) -> Unit = {}

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mBinding: FragmentCandidatePageBinding? = null

    private val viewModel: CandidatePageViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        initParam()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_candidate_page,
            container,
            false
        )
        init()
        return mBinding?.root
    }

    private fun init() {
        initCallback()
        initRV()
        observeData()

    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun initCallback() {
        removeCallback = {
            viewModel.setRecordGiveUp(it) {
                getData()
            }
        }
        signUpCallback = {

        }
        settlementCallback = {

        }
    }

    private fun initRV() {
        recyclerAdapter =
            CandidatePageRecyclerAdapter(removeCallback, signUpCallback, settlementCallback)
        mBinding!!.fragmentCandidatePageRv.adapter = recyclerAdapter
        mBinding!!.fragmentCandidatePageRv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeData() {
        viewModel.getRecordsListResult.observe(requireActivity()) {
            recyclerAdapter!!.addData(it)
        }
        viewModel.giveUpResult.observe(requireActivity(),{})
    }

    private fun getData() {
        viewModel.getRecordByType(
            MainActivity.EMPLOYER_ACCOUNT_BEAN!!.employerAccountId,
            paramType!!
        )
    }

    private fun initParam() {
        arguments?.let {
            paramType = it.getString(PARAM_TYPE)
        }
        if (paramType == null) {
            throw  Exception("paramType is null")
        }
    }

    companion object {

        val PARAM_TYPE = "param_type"

        @JvmStatic
        fun newInstance(paramType: String) =
            CandidatePageFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAM_TYPE, paramType)
                }
            }
    }
}