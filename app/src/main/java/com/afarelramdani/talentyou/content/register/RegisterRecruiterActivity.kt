package com.afarelramdani.talentyou.content.register

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.login.LoginActivity
import com.afarelramdani.talentyou.databinding.ActivityRegisterRecruiterBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.coroutines.*

class RegisterRecruiterActivity : BaseActivity<ActivityRegisterRecruiterBinding>(), View.OnClickListener {
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var viewModel: RegisterRecruiterViewModel
    private lateinit var service: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_register_recruiter
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main )
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)
        viewModel = ViewModelProvider(this).get(RegisterRecruiterViewModel::class.java)
        viewModel.setSharedPreference(sharePref)

        if (service != null) {
            viewModel.setRegisterService(service)
        }


        binding.btnRegisterRecruiter.setOnClickListener(this)
        subscribeLiveData()

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_register_recruiter -> {
                val name = binding.etNameRegister.text.toString()
                val email = binding.etEmailRegister.text.toString()
                val nohp = binding.etNohpRegister.text.toString()
                val password = binding.etPasswordRegister.text.toString()
                val companyName = binding.etCompanyRegister.text.toString()
                val companyPosition = binding.etPositionRegister.text.toString()
                val confirmPassword = binding.etConfirmpasswordRegister.text.toString()
                if (name.isEmpty() && email.isEmpty() && nohp.isEmpty() && password.isEmpty() && confirmPassword.isEmpty() && companyName.isEmpty() && companyPosition.isEmpty()) {
                    Toast.makeText(this, "Data tidak boleh kosong" , Toast.LENGTH_SHORT).show()
                } else if (password != confirmPassword) {
                    binding.tvConfirmPassword.error = getString(R.string.eror)
                }
                else {
                    viewModel.registerAccountRecruiter(name, nohp, email, password,  0, companyName, companyPosition)
                    Toast.makeText(this, "Daftar Berhasil" , Toast.LENGTH_SHORT).show()
                }
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