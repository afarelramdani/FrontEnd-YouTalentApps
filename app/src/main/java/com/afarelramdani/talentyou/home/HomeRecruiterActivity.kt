package com.afarelramdani.talentyou.home

import android.os.Bundle
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityHomeRecruiterBinding
import com.afarelramdani.talentyou.fragmentRecruiter.FragmentEmail
import com.afarelramdani.talentyou.fragmentRecruiter.FragmentHomeRecruiter
import com.afarelramdani.talentyou.fragmentRecruiter.FragmentProfileRecruiter



class HomeRecruiterActivity : BaseActivity<ActivityHomeRecruiterBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_home_recruiter
        super.onCreate(savedInstanceState)
        val fragmentHomeRecruiter = FragmentHomeRecruiter()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmentHomeRecruiter).commit()
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> {
                    val fragmentHomeRecruiter = FragmentHomeRecruiter()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmentHomeRecruiter).commit()
                    true
                }

                R.id.page_3 -> {
                    val fragmentEmail = FragmentEmail()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmentEmail).commit()
                    true
                }

                R.id.page_4 -> {
                    val fragmentProfileRecruiter = FragmentProfileRecruiter()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmentProfileRecruiter).commit()
                    true
                }
                else -> false
            }
        }

    }
}