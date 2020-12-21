package com.afarelramdani.talentyou.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityLoginTalentBinding
import com.afarelramdani.talentyou.home.HomeTalentActivity
import com.afarelramdani.talentyou.register.RegisterTalentActivity
import kotlinx.android.synthetic.main.activity_login_talent.*


class LoginTalentActivity : BaseActivity<ActivityLoginTalentBinding>(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_login_talent
        super.onCreate(savedInstanceState)

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
                var emailRegist = sharePref.getAccountEmail()

                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(this, "Tolong Masukkan Email Dan Password" , Toast.LENGTH_SHORT).show()
                } else if (email.isEmpty()) {
                    Toast.makeText(this, "Tolong Masukkan Email" , Toast.LENGTH_SHORT).show()
                } else if (password.isEmpty()) {
                    Toast.makeText(this, "Tolong Masukkan Password" , Toast.LENGTH_SHORT).show()
                } else {

                    if (email == emailRegist) {
                        sharePref.Remember(true)

                        sharePref.Github("afarelramdani")
                        baseStartActivity<HomeTalentActivity>(this)
                    } else {
                        Toast.makeText(this, "Data Belum Ada" , Toast.LENGTH_SHORT).show()
                    }

                }


            }

            R.id.tv_forget_password_talent -> {
                val moveForgetPassword = Intent(this, ForgetPasswordActivity::class.java)
                startActivity(moveForgetPassword)
            }

        }
    }
}