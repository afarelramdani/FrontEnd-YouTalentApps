package com.afarelramdani.talentyou.fragmentTalent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.FragmentProfileTalentBinding

import com.afarelramdani.talentyou.util.SharedPreferences
import com.afarelramdani.talentyou.webview.WebViewActivity

class FragmentProfileTalent: Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentProfileTalentBinding
    private lateinit var pageAdapter: ProfileTalentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_talent, container, false)
        pageAdapter = ProfileTalentAdapter(childFragmentManager)

        binding.tvGithubProfile.setOnClickListener(this)
        binding.btnEditHireTalent.setOnClickListener(this)


        var sharePref = context?.let { SharedPreferences(it) }
        var email = sharePref!!.getAccountEmail()
        binding.tvEmailProfile.text = email


        binding.viewPager.adapter = pageAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        return binding.root
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
                    ?.replace(R.id.fragment_container_home,  fragmentProfileTalent)
                    ?.commit()
            }
        }
    }

}