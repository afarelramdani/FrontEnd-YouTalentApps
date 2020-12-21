package com.afarelramdani.talentyou.fragmentRecruiter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.R

class ListTalentAdapter: RecyclerView.Adapter<ListTalentAdapter.EngineerList>() {

    val ltalent = listOf("Seo Dalmi Hans", "Farhan Ramdani", "Ajizul Hakim", "Putra", "Andini")
    val listImage = intArrayOf(R.drawable.dalmiku, R.drawable.ali, R.drawable.gitlab_icon, R.drawable.dalmiku, R.drawable.dalmiku )

    class EngineerList(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.iv_talent)
        var listTalent: TextView = itemView.findViewById(R.id.tv_name_talent)
    }

    override fun getItemCount(): Int = ltalent.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EngineerList {
        return EngineerList(LayoutInflater.from(parent.context).inflate(R.layout.list_engineer, parent, false))
    }

    override fun onBindViewHolder(holder: EngineerList, position: Int) {
        holder.listTalent.text = ltalent[position]
        holder.imageView.setImageResource(listImage[position])
    }
}