package com.afarelramdani.talentyou

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.afarelramdani.talentyou.databinding.ActivityMainBinding
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.content.login.LoginActivity

class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener {
    var isRemembered = false
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_main
        super.onCreate(savedInstanceState)

        val btnLoginRecruiter: Button = findViewById(R.id.btn_to_recruiter_login)
        btnLoginRecruiter.setOnClickListener(this)

        val btnLoginTalent: Button = findViewById(R.id.btn_to_talent_login)
        btnLoginTalent.setOnClickListener(this)


        isRemembered = sharePref.isLogin()
        if(isRemembered) {
            baseStartActivity<HomeActivity>(this)
        }

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_to_recruiter_login -> {
                baseStartActivity<LoginActivity>(this)
            }
            R.id.btn_to_talent_login -> {
                baseStartActivity<LoginActivity>(this)
            }


        }
    }
}