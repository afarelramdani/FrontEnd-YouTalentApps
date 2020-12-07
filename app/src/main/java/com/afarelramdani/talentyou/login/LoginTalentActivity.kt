package com.afarelramdani.talentyou.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
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
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_gotoregister_talent_login -> {
                val moveIntent = Intent(this, RegisterTalentActivity::class.java)
                startActivity(moveIntent)
            }

            R.id.btn_login_talent -> {
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
    }
}