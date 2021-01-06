package com.afarelramdani.talentyou.content.talent


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.afarelramdani.talentyou.BaseFragment
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.FragmentHomeTalentBinding
import com.afarelramdani.talentyou.util.SharedPreferences
import com.bumptech.glide.Glide

class FragmentHomeTalent: BaseFragment<FragmentHomeTalentBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_talent, container, false)
        var sharePref = context?.let { SharedPreferences(it) }
        var name = sharePref!!.getAccountName()


        var image = sharePref.getImageProfile()
        var img = "http://3.80.117.134:2000/image/$image"

        binding.tvNameTalent.text = name
        return binding.root
    }


}