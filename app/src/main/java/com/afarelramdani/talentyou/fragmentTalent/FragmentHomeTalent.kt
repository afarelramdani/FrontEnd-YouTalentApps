package com.afarelramdani.talentyou.fragmentTalent


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.afarelramdani.talentyou.BaseFragment
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.FragmentHomeTalentBinding
import com.afarelramdani.talentyou.util.SharedPreferences

class FragmentHomeTalent: BaseFragment<FragmentHomeTalentBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_talent, container, false)
        var sharePref = context?.let { SharedPreferences(it) }
        var name = sharePref!!.getAccountName()
        binding.textViewName.text = name
        return binding.root
    }


}