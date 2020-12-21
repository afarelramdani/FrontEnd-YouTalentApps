package com.afarelramdani.talentyou.hire

import android.os.Bundle
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityProfileTalentBinding
import com.afarelramdani.talentyou.experience.ExperienceFragment
import com.afarelramdani.talentyou.portofolio.PortofolioFragment
import kotlinx.android.synthetic.main.activity_profile_talent_hire.*

class ProfileTalentHireActivity : BaseActivity<ActivityProfileTalentBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_profile_talent_hire
        super.onCreate(savedInstanceState)

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