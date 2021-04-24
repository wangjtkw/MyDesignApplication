package com.example.mydesignapplication.ui.chatting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydesignapplication.R
import com.example.mydesignapplication.databinding.ActivityChattingBinding
import com.example.mydesignapplication.netty.NettyClient
import com.example.mydesignapplication.ui.MainActivity
import com.example.mydesignapplication.utils.ToastUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class ChattingActivity : AppCompatActivity(), HasAndroidInjector {

    private val TAG = "ChattingActivity"

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: ChattingViewModel by viewModels { viewModelFactory }

    private var mBinding: ActivityChattingBinding? = null

    private var param: ChattingRequestParam? = null

    private var mAdapter: ChattingRecyclerAdapter? = null

    private var haveNewData = false

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chatting)
        init()
    }


    private fun init() {
        getParam()
        setData()
        initRV()
        observeData()
        getData()
        initListener()
        initEdit()
    }

    private fun getParam() {
        param = intent.getParcelableExtra(CHATTING_PARAM)
        if (param == null) {
            ToastUtil.makeToast("请求参数错误！")
            finish()
        }
    }

    private fun getData() {
        viewModel.getUserInfo(param!!.receiverId)
        viewModel.getEmployerInfo(MainActivity.EMPLOYER_ACCOUNT_BEAN!!.employerAccountId)
        viewModel.getMsg(param!!.receiverId, MainActivity.EMPLOYER_ACCOUNT_BEAN!!.employerAccountId)
    }

    private fun observeData() {
        viewModel.employerResult.observe(this) {
            mAdapter!!.setEmployer(it)
        }
        viewModel.userResult.observe(this) {
            mAdapter!!.setUser(it)
        }

        viewModel.msgResult.observe(this) {
            Log.d(TAG, it.toString())
            mAdapter!!.addData(it)
            haveNewData = true
            mBinding!!.chattingRecycler.scrollToPosition(mAdapter!!.itemCount - 1);
        }
    }


    private fun initRV() {
        mAdapter = ChattingRecyclerAdapter()
        mBinding!!.chattingRecycler.apply {
            adapter = mAdapter
            val mLayoutManager = LinearLayoutManager(this@ChattingActivity)
            mLayoutManager.stackFromEnd = true
            layoutManager = mLayoutManager
        }

        mBinding!!.chattingRecycler.addOnLayoutChangeListener { view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            Log.d(TAG, "bottom:$bottom old:$oldBottom")
            if (bottom <= oldBottom && haveNewData) {

                mBinding!!.chattingRecycler.post {
                    if (mAdapter!!.itemCount > 0) {
                        mBinding!!.chattingRecycler.smoothScrollToPosition(mAdapter!!.itemCount - 1)
                        haveNewData = false
                    }
                }
            }
        }

    }

    private fun setData() {
        mBinding!!.chattingToolbarTitle.text = param!!.receiverName
    }

    private fun initListener() {
        mBinding!!.chattingToolbarBack.setOnClickListener {
            finish()
        }
        mBinding!!.chattingSendButton.setOnClickListener {
            val msg = mBinding!!.chattingMessageEdit.text.toString()
            NettyClient.sendMessage(param!!.receiverId, msg)
            mBinding!!.chattingMessageEdit.setText("")
        }

    }

    private fun initEdit() {
        val send = mBinding!!.chattingSendButton
        mBinding!!.chattingMessageEdit.addTextChangedListener(
            EditTextTextChangeListener({
                if (it.isEmpty()) {
                    send.isEnabled = false
                    send.background =
                        this.getDrawable(R.drawable.shape_send_button_corner_disabled)

                } else {
                    send.isEnabled = true
                    send.background =
                        this.getDrawable(R.drawable.shape_send_button_corner_available)
                }
            })
        )
    }


    companion object {

        const val CHATTING_PARAM = "chatting_param"

        fun startAction(context: Context, param: ChattingRequestParam) {
            val intent = Intent(context, ChattingActivity::class.java)
            intent.putExtra(CHATTING_PARAM, param)
            context.startActivity(intent)
        }
    }
}
