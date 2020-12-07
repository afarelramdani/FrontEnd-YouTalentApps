package com.afarelramdani.talentyou.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.recruiter.HomeRecruiterActivity
import com.afarelramdani.talentyou.register.RegisterRecruiterActivity
import kotlinx.android.synthetic.main.activity_login_recruiter.*
import kotlinx.android.synthetic.main.activity_login_talent.*
import kotlinx.android.synthetic.main.activity_login_talent.et_login_password

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
        val email = et_login_email_recruiter.text.toString()
        val password = et_login_password_recruiter.text.toString()


        when (v?.id) {
            R.id.btn_login_recruiter -> {
                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(this, "Tolong Masukkan Email Dan Password" , Toast.LENGTH_SHORT).show()
                } else if (email.isEmpty()) {
                    Toast.makeText(this, "Tolong Masukkan Email" , Toast.LENGTH_SHORT).show()
                } else if (password.isEmpty()) {
                    Toast.makeText(this, "Tolong Masukkan Password" , Toast.LENGTH_SHORT).show()
                } else {
                    val moveIntentToHomeRecruiter = Intent(this, HomeRecruiterActivity::class.java)
                    startActivity(moveIntentToHomeRecruiter)
                }

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