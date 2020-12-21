package com.afarelramdani.talentyou

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.afarelramdani.talentyou.databinding.ActivityMainBinding
import com.afarelramdani.talentyou.home.HomeTalentActivity
import com.afarelramdani.talentyou.login.LoginRecruiterActivity
import com.afarelramdani.talentyou.login.LoginTalentActivity
import com.afarelramdani.talentyou.webview.WebViewActivity

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
            baseStartActivity<HomeTalentActivity>(this)
        }

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_to_recruiter_login -> {
                baseStartActivity<LoginRecruiterActivity>(this)
            }
            R.id.btn_to_talent_login -> {
                val moveToRecruiterLogin = Intent(this@MainActivity, LoginTalentActivity::class.java)
                startActivity(moveToRecruiterLogin)
            }


        }
    }
}