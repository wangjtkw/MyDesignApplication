package com.example.mydesignapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.mydesignapplication.R

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {
    protected var mBinding: B? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParameters()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(
            layoutInflater,
            getLayoutID(),
            container,
            false,
        )
        init()
        return mBinding!!.root
    }

    abstract fun getLayoutID(): Int

    open fun initParameters() {}

    abstract fun init()

    protected fun <V> f(id: Int): V {
        return mBinding!!.root.findViewById(id)
    }

}