package com.example.mydesignapplication.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mydesignapplication.MainActivity
import com.example.mydesignapplication.R
import com.example.mydesignapplication.data.bean.Status
import com.example.mydesignapplication.databinding.ActivityLoginBinding
import com.example.mydesignapplication.databinding.ActivityRegisterBinding
import com.example.mydesignapplication.ui.companyInfo.CompanyInformationActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), HasAndroidInjector {
    private lateinit var accountEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerText: TextView

    private val TAG = "LoginActivity"

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val loginViewModel: LoginViewModel by viewModels { viewModelFactory }

    private var mBinding: ActivityLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        init()
        initListener()
    }

    private fun init() {
        accountEditText = f(R.id.activity_login_account_edit_text)
        passwordEditText = f(R.id.activity_login_password_edit_text)
        loginButton = f(R.id.activity_login_login_button)
        registerText = f(R.id.activity_login_register_text_view)
    }

    private fun initListener() {
        mBinding!!.activityLoginAccountEditText.setText("15213110106")
        mBinding!!.activityLoginPasswordEditText.setText("1300421975a")
        mBinding!!.activityLoginLoginButton.setOnClickListener {
            loginViewModel.login(
                mBinding!!.activityLoginAccountEditText.text.toString(),
                mBinding!!.activityLoginPasswordEditText.text.toString()
            ).observe(this) {
                when (it.status) {
                    Status.SUCCESS -> {
                        val employerAccountBean = it.data
                        if (employerAccountBean != null) {
                            Log.d(TAG, employerAccountBean.toString())
                            if (employerAccountBean.employerCompanyInfo != null) {
                                MainActivity.actionStart(this, employerAccountBean)
                            } else {
                                CompanyInformationActivity.actionStart(this, employerAccountBean)
                            }
                        }
                    }
                }
            }

        }
        mBinding!!.activityLoginRegisterTextView.setOnClickListener {
            RegisterActivity.actionStart(this)
        }
    }

    private fun <T : View> f(id: Int): T {
        return findViewById(id)
    }


    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}