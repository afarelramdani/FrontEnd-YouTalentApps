package com.afarelramdani.talentyou.fragmentRecruiter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.BaseFragment
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.FragmentProfileRecruiterBinding
import com.afarelramdani.talentyou.fragmentRecruiter.ListTalentAdapter

class FragmentEditProfileRecruiter: BaseFragment<FragmentProfileRecruiterBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_recruiter, container, false)
        return binding.root
    }
}