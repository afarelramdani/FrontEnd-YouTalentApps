package com.afarelramdani.talentyou.content.recruiter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.afarelramdani.talentyou.BaseFragment
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.FragmentProfileRecruiterBinding


class FragmentProfileRecruiter: BaseFragment<FragmentProfileRecruiterBinding>(), View.OnClickListener {
    private lateinit var pageAdapter: ProfileRecruiterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_recruiter, container, false)
        pageAdapter = ProfileRecruiterAdapter(childFragmentManager)
        binding.viewPager.adapter = pageAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        return binding.root
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_edit_profile_recruiter -> {
                val fragmentProfileRecruiter = FragmentEditProfileRecruiter()
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container_company,  fragmentProfileRecruiter)
                    ?.commit()
            }
        }
    }
}