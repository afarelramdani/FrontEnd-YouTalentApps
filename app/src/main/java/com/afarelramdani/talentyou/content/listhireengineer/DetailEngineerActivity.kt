package com.afarelramdani.talentyou.content.listhireengineer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.hire.AddHireActivity
import com.afarelramdani.talentyou.content.talent.ProfileTalentAdapter
import com.afarelramdani.talentyou.databinding.ActivityDetailEngineerBinding
import com.bumptech.glide.Glide

class DetailEngineerActivity : BaseActivity<ActivityDetailEngineerBinding>(), View.OnClickListener {
    private lateinit var pagerAdapter: ProfileTalentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_engineer
        super.onCreate(savedInstanceState)


        pagerAdapter = ProfileTalentAdapter(supportFragmentManager)
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        val name = intent.getStringExtra("name")
        binding.tvNameTalentProfile.text = name
        val jobType = intent.getStringExtra("jobType")
        binding.tvJobProfile.text = jobType
        val acEmail = intent.getStringExtra("acEmail")
        binding.tvEmailProfile.text = acEmail

        binding.btnHire.setOnClickListener(this)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener{
            onBackPressed()
        }


        val image = intent.getStringExtra("image")
        var img = "http://3.80.117.134:2000/image/$image"

        Glide.with(binding.ivPictureTalentProfile)
            .load(img)
            .placeholder(R.drawable.dalmiku)
            .error(R.drawable.dalmiku)
            .into(binding.ivPictureTalentProfile)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_hire -> {
                var toHire = Intent(this, AddHireActivity::class.java)
                startActivity(toHire)
            }
        }
    }


}