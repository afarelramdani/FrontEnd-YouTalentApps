package com.afarelramdani.talentyou.content.home

import android.os.Bundle
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.project.ProjectFragment
import com.afarelramdani.talentyou.databinding.ActivityHomeRecruiterBinding
import com.afarelramdani.talentyou.content.recruiter.FragmentEmail
import com.afarelramdani.talentyou.content.recruiter.FragmentHomeRecruiter
import com.afarelramdani.talentyou.content.recruiter.FragmentProfileRecruiter



class HomeRecruiterActivity : BaseActivity<ActivityHomeRecruiterBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_home_recruiter
        super.onCreate(savedInstanceState)


        val fragmentHomeRecruiter = FragmentHomeRecruiter()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_company, fragmentHomeRecruiter).commit()
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> {
                    val fragmentHomeRecruiter = FragmentHomeRecruiter()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_company, fragmentHomeRecruiter).commit()
                    true
                }

                R.id.page_3 -> {
                    val fragmentProject = ProjectFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_company, fragmentProject).commit()
                    true
                }

                R.id.page_4 -> {
                    val fragmentProfileRecruiter = FragmentProfileRecruiter()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_company, fragmentProfileRecruiter).commit()
                    true
                }
                else -> false
            }
        }

    }
}