package com.afarelramdani.talentyou

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(mainLooper).postDelayed({
            val movetoMain = Intent(this, MainActivity::class.java)
            startActivity(movetoMain)
            finish()
        }, 4000)
    }
}