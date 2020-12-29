package com.afarelramdani.talentyou.talent

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.afarelramdani.talentyou.experience.ExperienceFragment
import com.afarelramdani.talentyou.portofolio.PortofolioFragment

class ProfileTalentAdapter(fragment: FragmentManager): FragmentStatePagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
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