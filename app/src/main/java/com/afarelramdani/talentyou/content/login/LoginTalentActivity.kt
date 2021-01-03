package com.afarelramdani.talentyou.content.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityLoginTalentBinding
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.model.login.LoginResponse
import com.afarelramdani.talentyou.content.register.RegisterTalentActivity
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
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
                    loginAccountTalent(binding.etLoginEmail.text.toString(),  binding.etLoginPassword.text.toString())
                }


            }

            R.id.tv_forget_password_talent -> {
                val moveForgetPassword = Intent(this, ForgetPasswordActivity::class.java)
                startActivity(moveForgetPassword)
            }

        }
    }

    private fun loginAccountTalent(email: String ,password: String) {
        val service = ApiClient.getApiClient(context = this)!!.create(ApiService::class.java)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.loginAccount(email, password)
                } catch (t: Throwable) {
                    Log.e("Connetion Message", "${t.message}")
                }
            }

            if (response is LoginResponse) {
                if (response.success) {
                    Log.d("android hans", response.toString())
                    var response = response.data
                    sharePref.Remember(true)
                    sharePref.createAccountUser(response.acId, response.acName, response.acEmail, response.acLevel, response.token)
                    baseStartActivity<HomeActivity>(this@LoginTalentActivity)
                    finish()
                }


            }
        }
    }
}