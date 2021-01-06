package com.afarelramdani.talentyou.content.register

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityRegisterTalentBinding
import com.afarelramdani.talentyou.content.login.LoginTalentActivity
import com.afarelramdani.talentyou.model.register.RegisterTalentResponse
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.android.synthetic.main.activity_login_talent.*
import kotlinx.coroutines.*

class RegisterTalentActivity : BaseActivity<ActivityRegisterTalentBinding>() {
    private lateinit var coroutineScope: CoroutineScope
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_register_talent
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main )



        binding.btnRegister.setOnClickListener {
            val name = binding.etNameRegister.text.toString()
            val email = binding.etEmailRegister.text.toString()
            val nohp = binding.etNohpRegister.text.toString()
            val password = binding.etPasswordRegister.text.toString()
            val confirmPassword = binding.etConfirmpasswordRegister.text.toString()
            Toast.makeText(this, "Daftar Berhasil" , Toast.LENGTH_SHORT).show()
            if (name.isEmpty() && email.isEmpty() && nohp.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
                Toast.makeText(this, "Data tidak boleh kosong" , Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Data Password Harus Sama" , Toast.LENGTH_SHORT).show()
            }
            else {
                registerAccount()
            }

        }

    }

    private fun registerAccount() {
        val service = ApiClient.getApiClient(context = this)!!.create(ApiService::class.java)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.registerTalent(
                        name = binding.etNameRegister.text.toString(),
                        noHp = binding.etNohpRegister.text.toString(),
                        email = binding.etEmailRegister.text.toString(),
                        password = binding.etPasswordRegister.text.toString(),
                        accountLevel = 1

                    )
                } catch (t: Throwable) {
                    Log.e("msg", "${t.message}")
                    println("Data Tidak Ada")
                }
            }

            if (response is RegisterTalentResponse) {
                if(response?.success) {
                    baseStartActivity<LoginTalentActivity>(this@RegisterTalentActivity)
                } else {

                }

            }
        }
    }
}