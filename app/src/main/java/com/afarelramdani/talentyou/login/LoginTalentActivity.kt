package com.afarelramdani.talentyou.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.register.RegisterRecruiterActivity
import com.afarelramdani.talentyou.register.RegisterTalentActivity
import com.afarelramdani.talentyou.talent.ProfileTalentActivity
import kotlinx.android.synthetic.main.activity_login_talent.*

class LoginTalentActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_talent)

        val btnMoveToRegisterTalent: TextView = findViewById(R.id.tv_gotoregister_talent_login)
        btnMoveToRegisterTalent.setOnClickListener(this)

        val btnMoveToTalentProfile: Button = findViewById(R.id.btn_login_talent)
        btnMoveToTalentProfile.setOnClickListener(this)

        val tvGotoForgetPasswordTalent: TextView = findViewById(R.id.tv_forget_password_talent)
        tvGotoForgetPasswordTalent.setOnClickListener(this)




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
                    val moveToProfileTalent = Intent(this, ProfileTalentActivity::class.java)
                    val email = et_login_email.text.toString()
                    if(email.isEmpty() ) {
                        moveToProfileTalent.putExtra("email", "ahmadfarhanel@gmail.com")
                        startActivity(moveToProfileTalent)
                        finish()
                    } else {
                        moveToProfileTalent.putExtra("email", email)
                        startActivity(moveToProfileTalent)
                        finish()
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