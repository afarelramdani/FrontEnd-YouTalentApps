package com.afarelramdani.talentyou.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityLoginTalentBinding
import com.afarelramdani.talentyou.home.HomeTalentActivity
import com.afarelramdani.talentyou.model.login.LoginResponse
import com.afarelramdani.talentyou.register.RegisterTalentActivity
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.android.synthetic.main.activity_login_talent.*
import kotlinx.coroutines.*



class LoginTalentActivity : BaseActivity<ActivityLoginTalentBinding>(), View.OnClickListener {
    private lateinit var coroutineScope: CoroutineScope
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_login_talent
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main )

        binding.tvGotoregisterTalentLogin.setOnClickListener(this)
        binding.btnLoginTalent.setOnClickListener(this)
        binding.tvForgetPasswordTalent.setOnClickListener(this)



    }

    override fun onClick(v: View?) {
        val email = et_login_email.text.toString()
        val password = et_login_password.text.toString()

        when (v?.id) {
            R.id.tv_gotoregister_talent_login -> {
                val moveIntent = Intent(this, RegisterTalentActivity::class.java)
                startActivity(moveIntent)
            }

            R.id.btn_login_talent -> {

                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(this, "Tolong Masukkan Email Dan Password" , Toast.LENGTH_SHORT).show()
                } else if (email.isEmpty()) {
                    Toast.makeText(this, "Tolong Masukkan Email" , Toast.LENGTH_SHORT).show()
                } else if (password.isEmpty()) {
                    Toast.makeText(this, "Tolong Masukkan Password" , Toast.LENGTH_SHORT).show()
                } else {
                    loginAccount()
                }


            }

            R.id.tv_forget_password_talent -> {
                val moveForgetPassword = Intent(this, ForgetPasswordActivity::class.java)
                startActivity(moveForgetPassword)
            }

        }
    }

    private fun loginAccount() {
        val service = ApiClient.getApiClient(context = this)!!.create(ApiService::class.java)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.loginAccount(
                        email = binding.etLoginEmail.text.toString(),
                        password = binding.etLoginPassword.text.toString()
                    )
                } catch (t: Throwable) {
                    Log.e("msg", "${t.message}")
                    println("Data Tidak Ada")
                }
            }

            if (response is LoginResponse) {
                val response = response.data
                sharePref.createAccountUser(response.acName, response.acEmail, response.token)
                baseStartActivity<HomeTalentActivity>(this@LoginTalentActivity)
            }
        }
    }
}