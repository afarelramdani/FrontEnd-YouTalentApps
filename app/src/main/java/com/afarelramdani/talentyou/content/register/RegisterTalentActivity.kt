package com.afarelramdani.talentyou.content.register

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.databinding.ActivityRegisterTalentBinding
import com.afarelramdani.talentyou.content.login.LoginActivity
import com.afarelramdani.talentyou.content.login.LoginViewModel
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.android.synthetic.main.activity_login_talent.*
import kotlinx.coroutines.*

class RegisterTalentActivity : BaseActivity<ActivityRegisterTalentBinding>(), View.OnClickListener {
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var viewModel: RegisterTalentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_register_talent
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main )
        val service = ApiClient.getApiClient(this)?.create(ApiService::class.java)
        viewModel = ViewModelProvider(this).get(RegisterTalentViewModel::class.java)
        viewModel.setSharedPreference(sharePref)

        if (service != null) {
            viewModel.setRegisterService(service)
        }

        binding.btnRegisterTalent.setOnClickListener(this)
        binding.tvGotoTalentLogin.setOnClickListener(this)
        subscribeLiveData()

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_register_talent -> {
                val name = binding.etNameRegister.text.toString()
                val email = binding.etEmailRegister.text.toString()
                val noHp = binding.etNohpRegister.text.toString()
                val password = binding.etPasswordRegister.text.toString()
                val confirmPassword = binding.etConfirmpasswordRegister.text.toString()
                if (name.isEmpty() && email.isEmpty() && noHp.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
                    Toast.makeText(this, "Data tidak boleh kosong" , Toast.LENGTH_SHORT).show()
                } else if (password != confirmPassword) {
                    binding.tvConfirmPassword.error = getString(R.string.eror)
                }
                else {
                    viewModel.registerAccountTalent(name, email, noHp, password, 1)
                    Toast.makeText(this, "Daftar Berhasil" , Toast.LENGTH_SHORT).show()
                }
            }
            R.id.tv_goto_talent_login -> {
                baseStartActivity<LoginActivity>(this)
            }
        }
    }


    private fun subscribeLiveData() {
        viewModel.isRegisterLiveData.observe(this, Observer {
            Log.d("android1", "$it")

            if (it) {
                Toast.makeText(this, "Register Succcess", Toast.LENGTH_SHORT).show()
                baseStartActivity<LoginActivity>(this)
            } else {
                Toast.makeText(this, "Register Failed!", Toast.LENGTH_SHORT).show()
            }
        })

    }



}