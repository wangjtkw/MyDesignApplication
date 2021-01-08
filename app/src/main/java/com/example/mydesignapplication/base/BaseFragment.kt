package com.example.mydesignapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    protected lateinit var fragmentView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParameters()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentView = inflater.inflate(getLayoutID(), container, false)
        init()
        return fragmentView
    }

    abstract fun getLayoutID():Int

    open fun initParameters() {}

    abstract fun init()

    protected fun <T : View> f(id: Int): T {
        return fragmentView.findViewById(id)
    }
}