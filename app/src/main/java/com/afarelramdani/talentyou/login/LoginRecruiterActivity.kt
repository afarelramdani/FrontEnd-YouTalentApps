package com.afarelramdani.talentyou.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.recruiter.HomeRecruiterActivity
import com.afarelramdani.talentyou.register.RegisterRecruiterActivity

class LoginRecruiterActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_recruiter)

        val btnMoveToRegisterRecruiter: TextView = findViewById(R.id.tv_gotoregister_recruiter_login)
        btnMoveToRegisterRecruiter.setOnClickListener(this)

        val btnLoginSuccesRecruiter: Button = findViewById(R.id.btn_login_recruiter)
        btnLoginSuccesRecruiter.setOnClickListener(this)

        val tvGotoForgetPassword: TextView = findViewById(R.id.tv_forget_password)
        tvGotoForgetPassword.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login_recruiter -> {
                val moveIntentToHomeRecruiter = Intent(this, HomeRecruiterActivity::class.java)
                startActivity(moveIntentToHomeRecruiter)
            }


            R.id.tv_gotoregister_recruiter_login -> {
                val moveIntent = Intent(this, RegisterRecruiterActivity::class.java)
                startActivity(moveIntent)
            }

            R.id.tv_forget_password -> {
                val moveForgetPassword = Intent(this, ForgetPasswordActivity::class.java)
                startActivity(moveForgetPassword)
            }

        }
    }
}