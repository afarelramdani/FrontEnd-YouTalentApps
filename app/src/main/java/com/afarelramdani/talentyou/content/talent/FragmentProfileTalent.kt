package com.afarelramdani.talentyou.content.talent

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.BaseFragment
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.skill.SkillAdapter
import com.afarelramdani.talentyou.content.skill.SkillModel
import com.afarelramdani.talentyou.content.skill.SkillResponse
import com.afarelramdani.talentyou.content.skill.addskill.AddSkillActivity
import com.afarelramdani.talentyou.content.skill.addskill.deleteskill.DeleteSkillResponse
import com.afarelramdani.talentyou.content.talent.edittalent.FragmentEditProfileTalent
import com.afarelramdani.talentyou.databinding.FragmentProfileTalentBinding

import com.afarelramdani.talentyou.util.SharedPreferences
import com.afarelramdani.talentyou.content.webview.WebViewActivity
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.bumptech.glide.Glide
import kotlinx.coroutines.*

class FragmentProfileTalent: BaseFragment<FragmentProfileTalentBinding>(), SkillAdapter.OnSkillListClickListener, View.OnClickListener {
    private lateinit var pageAdapter: ProfileTalentAdapter
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ApiService
    var listSkill = ArrayList<SkillModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_talent, container, false)

        pageAdapter = ProfileTalentAdapter(childFragmentManager)

        sharePref = SharedPreferences(requireContext())
        service = context?.let { ApiClient.getApiClient(it) }!!.create(ApiService::class.java)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main )

        binding.tvGithubProfile.setOnClickListener(this)
        binding.btnEditHireTalent.setOnClickListener(this)
        binding.btnAddSkill.setOnClickListener(this)

        binding.viewPager.adapter = pageAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.rvSkill.adapter = SkillAdapter(listSkill, this)
        binding.rvSkill.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        getDataSkillEngineer(sharePref.getEngineerId())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sharePref = context?.let { SharedPreferences(it) }
        var email = sharePref!!.getAccountEmail()
        binding.tvEmailProfile.text = email

        var name = sharePref!!.getAccountName()
        binding.tvNameTalentProfile.text = name

        if (sharePref.getImageProfile() != null) {
            var image = sharePref.getImageProfile()
            var img = "http://3.80.117.134:2000/image/$image"

            Glide.with(binding.ivPictureTalentProfile)
                .load(img)
                .placeholder(R.drawable.defaultimage)
                .error(R.drawable.defaultimage)
                .into(binding.ivPictureTalentProfile)
        } else {
            var img = R.drawable.defaultimage
            Glide.with(binding.ivPictureTalentProfile)
                .load(img)
                .placeholder(R.drawable.defaultimage)
                .error(R.drawable.defaultimage)
                .into(binding.ivPictureTalentProfile)
        }
        0

        if(sharePref.getJobTitle() != null ) {
            var jobTitle = sharePref.getJobTitle()
            binding.tvJobTitle.text = jobTitle
        } else {
            binding.tvJobTitle.text = ""
        }

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_github_profile -> {
                val moveToWebView = Intent(activity, WebViewActivity::class.java)
                startActivity(moveToWebView)
            }
            R.id.btn_edit_hire_talent -> {
                val fragmentProfileTalent = FragmentEditProfileTalent()
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container,  fragmentProfileTalent)
                    ?.commit()
            }

            R.id.btn_add_skill -> {
                val addSkill = Intent(activity, AddSkillActivity::class.java)
                startActivity(addSkill)
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

    private fun deleteDataSkillEngineer(id: Int) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.deleteSkill(id)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (result is DeleteSkillResponse) {
                Log.d("dataskill", result.toString())
                if(result.success) {
                    if (result.success) {
                        message("Delete Succes")
                    }
                }
            }
        }
    }

    private fun showDialogDeleteSkill(position: Int) {
        val id = listSkill[position].skillId
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Delete Skill")
        builder.setMessage("Do you want to delete ${listSkill[position].skillName} your skill ?")
        builder.setPositiveButton("Yes") { dialogInterface : DialogInterface, i : Int ->
            deleteDataSkillEngineer(id!!)
            val fragmentProfileTalent = FragmentProfileTalent()
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container,  fragmentProfileTalent)
                ?.commit()
        }
        builder.setNegativeButton("No") { dialogInterface : DialogInterface, i : Int ->}
        builder.show()
    }

    private fun message(message : String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClicked(position: Int) {
        showDialogDeleteSkill(position)
    }


}