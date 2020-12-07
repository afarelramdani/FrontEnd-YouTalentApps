package com.afarelramdani.talentyou.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.inbox.InboxActivity
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPasswordActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        val btnMoveToResetConfirmation: Button = findViewById(R.id.btn_login_reset)
        btnMoveToResetConfirmation.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_login_reset -> {
                val moveToResetConfirmation = Intent(this, ForgetPasswordConfirmationActivity::class.java)
                startActivity(moveToResetConfirmation)
            }
        }
    }
}