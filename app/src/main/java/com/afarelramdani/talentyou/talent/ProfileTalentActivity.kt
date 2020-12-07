package com.afarelramdani.talentyou.talent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.inbox.InboxActivity
import com.afarelramdani.talentyou.recruiter.ExperienceFragment
import com.afarelramdani.talentyou.recruiter.PortofolioFragment
import kotlinx.android.synthetic.main.activity_profile_talent.*
import kotlinx.android.synthetic.main.activity_profile_talent_hire.*
import kotlinx.android.synthetic.main.activity_profile_talent_hire.tv_experience_detail
import kotlinx.android.synthetic.main.activity_profile_talent_hire.tv_portofolio_detail

class ProfileTalentActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_talent)

        val tvEmail: TextView = findViewById(R.id.tv_email_profile)

        val email = intent.getStringExtra("email")
        tvEmail.text = email

        val fragment1 = PortofolioFragment()
        val fragment2 = ExperienceFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fr_fragment_experience_portofolio, fragment1)
            commit()
        }

        tv_portofolio_detail.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fr_fragment_experience_portofolio, fragment1)
                commit()
            }
        }
        tv_experience_detail.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fr_fragment_experience_portofolio, fragment2)
                commit()
            }
        }

        val btnEditHireTalent: Button = findViewById(R.id.btn_edit_hire_talent)
        btn_edit_hire_talent.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_edit_hire_talent -> {
                val moveToEditProfile = Intent(this, ProfileExperienceActivity::class.java)
                startActivity(moveToEditProfile)
            }
        }
    }
}