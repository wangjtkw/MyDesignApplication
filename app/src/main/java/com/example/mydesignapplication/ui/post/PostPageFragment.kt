package com.example.mydesignapplication.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mydesignapplication.base.BaseFragment
import com.example.mydesignapplication.R
import com.example.mydesignapplication.data.bean.Status
import com.example.mydesignapplication.databinding.FragmentPostBinding
import com.example.mydesignapplication.databinding.FragmentPostPageBinding
import com.example.mydesignapplication.ui.MainActivity
import com.example.mydesignapplication.ui.mine.MineViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

private const val ARG_PARAM_TYPE = "type"

class PostPageFragment : Fragment() {
    private var paramType: String = ""

    private lateinit var postPageReleaseJobLayout: FrameLayout

    private var mAdapter: PostPageRecyclerAdapter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PostPageViewModel by viewModels { viewModelFactory }
    private var mBinding: FragmentPostPageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        initParameters()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_post_page,
            container,
            false,
//            dataBindingComponent
        )
        initRV()
        init()
        return mBinding?.root
    }

    fun init() {
        viewModel.getPositionList(MainActivity.EMPLOYER_ACCOUNT_BEAN!!.employerAccountId, paramType)
            .observe(requireActivity()) {
                when (it.status) {
                    Status.SUCCESS -> {
                        if (it.data.isNullOrEmpty()) {
                            mBinding!!.postPageReleaseJobLayout.visibility = View.VISIBLE
                            mBinding!!.postPageRecyclerView.visibility = View.GONE
                        } else {
                            mBinding!!.postPageReleaseJobLayout.visibility = View.GONE
                            mBinding!!.postPageRecyclerView.visibility = View.VISIBLE
                            mAdapter!!.addData(it.data)

                        }
                    }
                }
            }
    }

    private fun initRV() {
        mAdapter = PostPageRecyclerAdapter()
        mBinding!!.postPageRecyclerView.adapter = mAdapter
        mBinding!!.postPageRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initParameters() {
        arguments?.let {
            paramType = it.getString(ARG_PARAM_TYPE) ?: ""
        }
    }


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