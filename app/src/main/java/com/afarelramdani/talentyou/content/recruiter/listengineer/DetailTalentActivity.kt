package com.afarelramdani.talentyou.content.recruiter.listengineer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.hire.addhire.AddHireActivity
import com.afarelramdani.talentyou.content.skill.SkillAdapter
import com.afarelramdani.talentyou.content.skill.SkillModel
import com.afarelramdani.talentyou.content.skill.SkillResponse
import com.afarelramdani.talentyou.content.talent.ProfileTalentAdapter
import com.afarelramdani.talentyou.databinding.ActivityDetailTalentBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.bumptech.glide.Glide
import kotlinx.coroutines.*

class DetailTalentActivity : BaseActivity<ActivityDetailTalentBinding>(), SkillAdapter.OnSkillListClickListener, View.OnClickListener {
    private lateinit var pagerAdapter: ProfileTalentAdapter
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ApiService
    var listSkill = ArrayList<SkillModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_talent
        super.onCreate(savedInstanceState)

        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main )


        pagerAdapter = ProfileTalentAdapter(supportFragmentManager)
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)


        if (sharePref.getAccountLevel() == 1) {
            binding.btnHire.visibility = View.GONE
        }
        binding.rvSkill.adapter = SkillAdapter(listSkill, this)
        binding.rvSkill.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        val name = intent.getStringExtra("name")
        binding.tvNameTalentProfile.text = name
        val jobType = intent.getStringExtra("jobType")
        binding.tvJobProfile.text = jobType
        val acEmail = intent.getStringExtra("acEmail")
        binding.tvEmailProfile.text = acEmail
        val location = intent.getStringExtra("location")
        binding.tvLocationProfile.text = location
        val desc = intent.getStringExtra("desc")
        binding.tvDescProfile.text = desc

        getDataSkillEngineer(sharePref.getDetailEngineerId())

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
            .placeholder(R.drawable.defaultimage)
            .error(R.drawable.defaultimage)
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

    private fun getDataSkillEngineer(id: Int) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getSkillByEngineerId(id)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (result is SkillResponse) {
                Log.d("dataskill", result.toString())
                if(result.success) {
                    val list = result.data.map {
                        SkillModel(it.skillId, it.engineerId, it.skillName)
                    }
                    (binding.rvSkill.adapter as SkillAdapter).addList(list)
                }
            }
        }
    }

    override fun onItemClicked(position: Int) {
        TODO("Not yet implemented")
    }


}