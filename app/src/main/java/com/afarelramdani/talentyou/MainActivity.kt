package com.afarelramdani.talentyou

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.afarelramdani.talentyou.login.LoginRecruiterActivity
import com.afarelramdani.talentyou.login.LoginTalentActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLoginRecruiter: Button = findViewById(R.id.btn_to_recruiter_login)
        btnLoginRecruiter.setOnClickListener(this)

        val btnLoginTalent: Button = findViewById(R.id.btn_to_talent_login)
        btnLoginTalent.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_to_recruiter_login -> {
                val moveToRecruiterLogin = Intent(this@MainActivity, LoginRecruiterActivity::class.java)
                startActivity(moveToRecruiterLogin)
            }
            R.id.btn_to_talent_login -> {
                val moveToRecruiterLogin = Intent(this@MainActivity, LoginTalentActivity::class.java)
                startActivity(moveToRecruiterLogin)
            }
        }
    }
}