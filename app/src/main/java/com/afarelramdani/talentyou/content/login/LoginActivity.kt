package com.afarelramdani.talentyou.content.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityLoginTalentBinding
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.content.register.OnBoardRegisterActivity
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.android.synthetic.main.activity_login_talent.*
import kotlinx.coroutines.*



class LoginActivity : BaseActivity<ActivityLoginTalentBinding>(), View.OnClickListener {
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var viewModel: LoginViewModel
    private lateinit var service: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_login_talent
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main )
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.setSharedPreference(sharePref)
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)

        service()

        binding.tvGotoregisterTalentLogin.setOnClickListener(this)
        binding.btnLoginTalent.setOnClickListener(this)
        binding.tvForgetPasswordTalent.setOnClickListener(this)

        subscribeLiveData()
    }

    fun service(){
        if (service != null) {
            viewModel.setLoginService(service)
        }
    }

    override fun onClick(v: View?) {
        val email = et_login_email.text.toString()
        val password = et_login_password.text.toString()

        when (v?.id) {
            R.id.tv_gotoregister_talent_login -> {
                baseStartActivity<OnBoardRegisterActivity>(this)
            }

            R.id.btn_login_talent -> {

                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(this, "Tolong Masukkan Email Dan Password" , Toast.LENGTH_SHORT).show()
                } else if (email.isEmpty()) {
                    Toast.makeText(this, "Tolong Masukkan Email" , Toast.LENGTH_SHORT).show()
                } else if (password.isEmpty()) {
                    Toast.makeText(this, "Tolong Masukkan Password" , Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.loginAccountTalent(binding.etLoginEmail.text.toString(),  binding.etLoginPassword.text.toString())
                }

            }

            R.id.tv_forget_password_talent -> {
                baseStartActivity<ForgetPasswordActivity>(this)
            }

        }
    }

    private fun subscribeLiveData() {
        viewModel.isLoginLiveData.observe(this, {
            Log.d("android1", "$it")

            if (it) {
                Toast.makeText(this, "Login Succcess", Toast.LENGTH_SHORT).show()
                sharePref.createTempPassword(binding.etLoginPassword.text.toString())
                baseStartActivity<HomeActivity>(this)
                finish()
            } else {
                binding.tvPassword.error = getString(R.string.failedlogin)
            }
        })

    }


}