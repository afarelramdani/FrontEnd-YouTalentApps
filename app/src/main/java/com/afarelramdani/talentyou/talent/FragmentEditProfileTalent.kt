package com.afarelramdani.talentyou.talent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.afarelramdani.talentyou.BaseFragment
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.FragmentEditProfileTalentBinding
import com.afarelramdani.talentyou.util.SharedPreferences

class FragmentEditProfileTalent: BaseFragment<FragmentEditProfileTalentBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile_talent, container, false)
        return binding.root
    }

}