package com.afarelramdani.talentyou.content.experience

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.R

class FragmentExperienceAdapter: RecyclerView.Adapter<FragmentExperienceAdapter.ExperienceList>() {
    val listImage = intArrayOf(R.drawable.ali, R.drawable.ali, R.drawable.gitlab_icon, R.drawable.dalmiku, R.drawable.dalmiku )
    val listpostition = listOf("Android Developer", "Front End Developer", "Full Stack Developer", "Front End", "Front End")
    val listCompany = listOf("TOKOPEDIA", "Ali Baba", "Google", "Shopee", "Asurance")

    class ExperienceList(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.iv_experience)
        var listPosition: TextView = itemView.findViewById(R.id.tv_type_experience)
        var companyList: TextView = itemView.findViewById(R.id.tv_name_company)
    }

    override fun getItemCount(): Int = listpostition.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceList {
        return ExperienceList(LayoutInflater.from(parent.context).inflate(R.layout.list_experience, parent, false))
    }

    override fun onBindViewHolder(holder: ExperienceList, position: Int) {
        holder.imageView.setImageResource(listImage[position])
        holder.listPosition.text = listpostition[position]
        holder.companyList.text = listCompany[position]
    }


}