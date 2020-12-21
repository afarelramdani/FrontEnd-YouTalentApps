package com.afarelramdani.talentyou.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityRegisterTalentBinding
import com.afarelramdani.talentyou.login.LoginTalentActivity
import kotlinx.android.synthetic.main.activity_login_talent.*

class RegisterTalentActivity : BaseActivity<ActivityRegisterTalentBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_register_talent
        super.onCreate(savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmailRegister.text.toString()
            val nama = binding.etNameRegister.text.toString()
            sharePref.createAccountUser(nama, email)
            Toast.makeText(this, "Daftar Berhasil" , Toast.LENGTH_SHORT).show()
            baseStartActivity<LoginTalentActivity>(this)
        }

    }
}