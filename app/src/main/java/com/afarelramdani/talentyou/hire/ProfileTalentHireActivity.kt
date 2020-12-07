package com.afarelramdani.talentyou.hire

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.recruiter.ExperienceFragment
import com.afarelramdani.talentyou.recruiter.PortofolioFragment
import kotlinx.android.synthetic.main.activity_profile_talent_hire.*

class ProfileTalentHireActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_talent_hire)

        val fragment1 = PortofolioFragment()
        val fragment2 = ExperienceFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fr_fragment_experience_hire_portofolio, fragment1)
            commit()
        }

        tv_portofolio_detail.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fr_fragment_experience_hire_portofolio, fragment1)
                commit()
            }
        }
        tv_experience_detail.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fr_fragment_experience_hire_portofolio, fragment2)
                commit()
            }
        }
    }
}