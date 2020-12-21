package com.afarelramdani.talentyou.talent

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityProfileTalentBinding
import com.afarelramdani.talentyou.fragmentTalent.ProfileTalentAdapter

class ProfileTalentActivity : BaseActivity<ActivityProfileTalentBinding>(), View.OnClickListener {
    private lateinit var pageAdapter: ProfileTalentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_profile_talent
        super.onCreate(savedInstanceState)


        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener{
            onBackPressed()
        }


        pageAdapter = ProfileTalentAdapter(supportFragmentManager)
        binding.viewPager.adapter = pageAdapter

        binding.tabLayout.setupWithViewPager(binding.viewPager)

        val btnEditHireTalent = binding.btnEditHireTalent
        btnEditHireTalent.setOnClickListener(this)
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