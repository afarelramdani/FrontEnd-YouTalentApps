package com.afarelramdani.talentyou.content.skill

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.projectrecruiter.ProjectModel
import com.afarelramdani.talentyou.databinding.ListSkillBinding

class SkillAdapter(private val skillList: ArrayList<SkillModel>, private val onItemSkillClickListener: OnSkillListClickListener): RecyclerView.Adapter<SkillAdapter.SkillHolder>() {


    fun addList(list: List<SkillModel>) {
        skillList.clear()
        skillList.addAll(list)
        notifyDataSetChanged()
    }

    class SkillHolder(val binding: ListSkillBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillHolder {
        return SkillHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_skill, parent, false))
    }

    override fun getItemCount(): Int = skillList.size

    override fun onBindViewHolder(holder: SkillHolder, position: Int) {
        val item = skillList[position]

        holder.binding.tvSkill.text = item.skillName
        holder.binding.tvSkill.setOnClickListener{
            onItemSkillClickListener.onItemClicked(position)
        }
    }

    interface OnSkillListClickListener {
        fun onItemClicked(position: Int)
    }

}