package com.afarelramdani.talentyou

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class SplashScreen : AppCompatActivity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        var topAnimation = AnimationUtils.loadAnimation(this, R.animator.top_animation)
//        var botAnimation = AnimationUtils.loadAnimation(this, R.animator.bottom_animation)
//
//        var image = findViewById<ImageView>(R.id.iv_splashcreen)
//        var name = findViewById<TextView>(R.id.tv_splashscreen)
//
//        image.startAnimation(topAnimation)
//        name.startAnimation(botAnimation)

        Handler(mainLooper).postDelayed({
            val movetoMain = Intent(this, MainActivity::class.java)
            startActivity(movetoMain)
            finish()
        }, 4000)
    }
}