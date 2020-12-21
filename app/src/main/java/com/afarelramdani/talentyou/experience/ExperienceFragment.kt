package com.afarelramdani.talentyou.experience

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.BaseFragment
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.FragmentExperienceBinding


class ExperienceFragment : BaseFragment<FragmentExperienceBinding>() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_experience, container, false)
        binding.rvListRecruiter.adapter = FragmentExperienceAdapter()
        binding.rvListRecruiter.layoutManager = LinearLayoutManager(activity,
            RecyclerView.VERTICAL,false)
        return binding.root
    }
}