package com.afarelramdani.talentyou.content.skill.addskill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.experience.addexperience.AddExperienceResponse
import com.afarelramdani.talentyou.content.experience.addexperience.AddExperienceViewModel
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.databinding.ActivityAddSkillBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.coroutines.*

class AddSkillActivity : BaseActivity<ActivityAddSkillBinding>(), View.OnClickListener {
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var addSkillViewModel: AddSkillViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_add_skill
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)
        addSkillViewModel = ViewModelProvider(this).get(AddSkillViewModel::class.java)
        addSkillViewModel.setSharedPreference(sharePref)

        binding.btnAddSkillData.setOnClickListener(this)

        service()
        subscribeLiveData()
        setToolbarActionBar()
    }


    fun service() {
        if (service != null) {
            addSkillViewModel.setAddSkillService(service)
        }
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Skill"
        binding.toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_add_skill_data -> {
                val enId = sharePref.getEngineerId()
                val skillName = binding.etSkill.text.toString()
                addSkillViewModel.AddSkill(enId, skillName)
            }
        }
    }

    private fun subscribeLiveData() {
        addSkillViewModel.isAddSkillLiveData.observe(this, {
            Log.d("android1", "$it")
            if (it) {
                Toast.makeText(this, "Add Skill Succcess", Toast.LENGTH_SHORT).show()
                baseStartActivity<HomeActivity>(this)
            } else {
                Toast.makeText(this, "Add Skill Failed!", Toast.LENGTH_SHORT).show()
            }
        })

    }
}