package com.afarelramdani.talentyou.recruiter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.hire.ProfileTalentHireActivity

class RecruiterProfileActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruiter_profile)

        val btnEditProfile: Button = findViewById(R.id.btn_edit_profile)
        btnEditProfile.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_edit_profile -> {
                val moveToProfileEdit = Intent(this, EditRecruiterActivity::class.java)
                startActivity(moveToProfileEdit)
            }
        }
    }
}