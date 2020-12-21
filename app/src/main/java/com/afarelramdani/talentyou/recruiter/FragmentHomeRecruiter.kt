package com.afarelramdani.talentyou.recruiter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.BaseFragment
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.FragmentHomeRecruiterBinding
import com.afarelramdani.talentyou.fragmentRecruiter.ListTalentAdapter


class FragmentHomeRecruiter: BaseFragment<FragmentHomeRecruiterBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_recruiter, container, false)
        binding.rvListRecruiter.adapter = ListTalentAdapter()
        binding.rvListRecruiter.layoutManager = LinearLayoutManager(activity,
            RecyclerView.VERTICAL,false)
        return binding.root
    }

}