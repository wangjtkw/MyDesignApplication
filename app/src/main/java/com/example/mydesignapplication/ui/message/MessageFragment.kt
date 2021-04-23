package com.example.mydesignapplication.ui.message

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
import com.example.mydesignapplication.databinding.FragmentMessageBinding
import com.example.mydesignapplication.databinding.FragmentMineBinding
import com.example.mydesignapplication.ui.MainActivity
import com.example.mydesignapplication.ui.mine.MineViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MessageFragment : Fragment() {
    private val TAG = "MessageFragment"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mBinding: FragmentMessageBinding? = null

    private val viewModel: MessageViewModel by viewModels { viewModelFactory }

    private var mAdapter: MessageRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_message,
            container,
            false
        )
        init()
        return mBinding!!.root
    }

    private fun init() {
        initRV()
        observeData()
        getData()
    }

    private fun observeData() {
        viewModel.msgInfoResult.observe(requireActivity()) {
            mAdapter!!.addData(it)
        }

        viewModel.userInfoResult.observe(requireActivity()) {

        }
    }

    private fun getData() {
        viewModel.getInfo(MainActivity.EMPLOYER_ACCOUNT_BEAN!!.employerAccountId)
    }

    private fun initRV() {
        mAdapter = MessageRecyclerAdapter()
        mBinding!!.messageRv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            MessageFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}