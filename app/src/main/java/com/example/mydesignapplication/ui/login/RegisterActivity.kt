package com.example.mydesignapplication.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mydesignapplication.R
import com.example.mydesignapplication.data.bean.Status
import com.example.mydesignapplication.databinding.ActivityRegisterBinding
import com.example.mydesignapplication.ext.isConnectedNetwork
import com.example.mydesignapplication.utils.ToastUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class RegisterActivity : AppCompatActivity(), HasAndroidInjector {
    private val TAG = "RegisterActivity"
    private lateinit var backLayout: FrameLayout
    private lateinit var accountEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordAgainEditText: EditText
    private lateinit var registerButton: Button

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val registerViewModel: RegisterViewModel by viewModels { viewModelFactory }

    private var mBinding: ActivityRegisterBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        init()
        initListener()
    }

    private fun init() {
        accountEditText = f(R.id.activity_register_account_edit_text)
        passwordEditText = f(R.id.activity_register_password_edit_text)
        passwordAgainEditText = f(R.id.activity_register_password_again_edit_text)
        registerButton = f(R.id.activity_register_register_button)
    }

    private fun initListener() {
        mBinding!!.activityRegisterBackLayout.setOnClickListener {
            finish()
        }
        mBinding!!.activityRegisterRegisterButton.setOnClickListener {
            val account = mBinding!!.activityRegisterAccountEditText.text.toString()
            val password = mBinding!!.activityRegisterPasswordEditText.text.toString()
            val passwordRepeat = mBinding!!.activityRegisterPasswordAgainEditText.text.toString()
            if (!isConnectedNetwork()){
                ToastUtil.makeToast("当前网络未连接")
            }
            registerViewModel.register(account, password, passwordRepeat) {
                LoginActivity.actionStart(this)
            }
        }

        registerViewModel.registerResult.observe(this){
            Log.d(TAG,"run")
        }
    }

    private fun initEditText() {

    }

    private fun <T : View> f(id: Int): T {
        return findViewById(id)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    companion object{
        fun actionStart(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }
}