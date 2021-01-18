package com.afarelramdani.talentyou

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.afarelramdani.talentyou.databinding.ActivityMainBinding
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.content.login.LoginActivity
import com.afarelramdani.talentyou.content.register.OnBoardRegisterActivity

class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener {
    var isRemembered = false
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_main
        super.onCreate(savedInstanceState)


        binding.btnLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)

        isRemembered = sharePref.isLogin()
        if(isRemembered) {
            baseStartActivity<HomeActivity>(this)
        }

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_login -> {
                baseStartActivity<LoginActivity>(this)
            }
            R.id.btn_register -> {
                baseStartActivity<OnBoardRegisterActivity>(this)
            }
        }
    }
}