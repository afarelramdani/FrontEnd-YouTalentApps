package com.afarelramdani.talentyou.content.recruiter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.loader.content.CursorLoader
import com.afarelramdani.talentyou.BaseFragment
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.recruiter.editrecruiter.EditRecruiterActivity
import com.afarelramdani.talentyou.content.recruiter.editrecruiter.EditRecruiterImageActivity
import com.afarelramdani.talentyou.databinding.FragmentProfileRecruiterBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


class FragmentProfileRecruiter: BaseFragment<FragmentProfileRecruiterBinding>(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_recruiter, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sharePref = context?.let { SharedPreferences(it) }
        var email = sharePref!!.getAccountEmail()

        binding.tvEmailProfile.text = email

        var name = sharePref!!.getCompanyName()
        binding.tvNameTalentProfile.text = name

        var image = sharePref.getImageProfile()
        var img = "http://3.80.117.134:2000/image/$image"

        var position = sharePref.getCompanyPosition()
        binding.tvJobProfile.text = position

        var location = sharePref.getCompanyLocation()
        binding.tvLocationProfile.text = location

        var desc = sharePref.getCompanyDesc()
        binding.tvDescProfile.text = desc

        var ig = sharePref.getCompanyInstagram()
        binding.tvIgProfile.text = ig

        var linkedin = sharePref.getCompanyLinkedin()
        binding.tvLinkedin.text = linkedin



        Glide.with(binding.ivPictureTalentProfile)
            .load(img)
            .placeholder(R.drawable.defaultimage)
            .error(R.drawable.defaultimage)
            .into(binding.ivPictureTalentProfile)

        binding.btnEditProfileRecruiter.setOnClickListener(this)
        binding.tvEditProfileRecruiter.setOnClickListener(this)
        }



    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_edit_profile_recruiter -> {
                val editProfileCompany = Intent(activity, EditRecruiterActivity::class.java)
                requireActivity().startActivity(editProfileCompany)
            }
            R.id.tv_edit_profile_recruiter -> {
                val editProfileCompany = Intent(activity, EditRecruiterImageActivity::class.java)
                requireActivity().startActivity(editProfileCompany)
            }
        }
    }

}