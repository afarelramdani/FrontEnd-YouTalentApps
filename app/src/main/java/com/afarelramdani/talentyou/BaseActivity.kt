package com.afarelramdani.talentyou

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.afarelramdani.talentyou.util.SharedPreferences

abstract class BaseActivity<ActivityBase: ViewDataBinding> : AppCompatActivity() {
    protected lateinit var binding: ActivityBase
    protected var setLayout: Int? = null
    protected lateinit var sharePref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@BaseActivity, setLayout!!)
        sharePref = SharedPreferences(this)

    }

    protected inline fun <reified Activity> baseStartActivity(context: Context) {
        context.startActivity(Intent(context, Activity::class.java))
    }

}