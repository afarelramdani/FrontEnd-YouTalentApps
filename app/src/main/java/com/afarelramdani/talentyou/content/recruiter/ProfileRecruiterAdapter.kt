package com.afarelramdani.talentyou.content.recruiter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.afarelramdani.talentyou.content.experience.ExperienceFragment
import com.afarelramdani.talentyou.content.portofolio.PortofolioFragment

class ProfileRecruiterAdapter(fragment: FragmentManager): FragmentStatePagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    val fragment = arrayOf(PortofolioFragment(), ExperienceFragment())

    override fun getCount(): Int = fragment.size

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Portofolio"
            1 -> "Experience"
            else -> ""
        }
    }

}