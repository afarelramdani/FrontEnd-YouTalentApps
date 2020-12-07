package com.afarelramdani.talentyou.recruiter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.hire.ProfileTalentHireActivity
import com.afarelramdani.talentyou.inbox.InboxActivity
import com.afarelramdani.talentyou.talent.ProfileExperienceActivity
import kotlinx.android.synthetic.main.activity_home_recruiter.*

class HomeRecruiterActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_recruiter)

        val btnMessageMenu: Button = findViewById(R.id.btn_message_menu)
        btnMessageMenu.setOnClickListener(this)

        val btnProfileMenu: Button = findViewById(R.id.btn_profile_menu)
        btnProfileMenu.setOnClickListener(this)

        val btnHome = findViewById<ConstraintLayout>(R.id.frame_1_home)
        btnHome.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v?.id)  {
            R.id.frame_1_home -> {
                val moveToProfile = Intent(this, ProfileTalentHireActivity::class.java)
                startActivity(moveToProfile)
            }

            R.id.btn_message_menu -> {
                val moveToInbox = Intent(this, InboxActivity::class.java)
                startActivity(moveToInbox)
            }

            R.id.btn_profile_menu -> {
                val moveToExperience = Intent(this, ProfileExperienceActivity::class.java)
                startActivity(moveToExperience)
            }
        }
    }
}