package com.afarelramdani.talentyou.content.projectrecruiter.detailproject.listhire

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.afarelramdani.talentyou.content.projectrecruiter.detailproject.listhire.FragmentHireListApprove
import com.afarelramdani.talentyou.content.projectrecruiter.detailproject.listhire.FragmentHireListReject

class DetailProjectTabAdapter(fragment: FragmentManager) : FragmentStatePagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val fragment = arrayOf(
        FragmentHireListApprove(),
        FragmentHireListReject()
    )

    override fun getCount():Int = fragment.size

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0-> "Approve"
            1 -> "Reject"
            else -> ""
        }
    }

}