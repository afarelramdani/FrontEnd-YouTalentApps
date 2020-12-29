package com.afarelramdani.talentyou.project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ItemProjectBinding
import com.afarelramdani.talentyou.model.project.ProjectModel
import java.util.*

class ProjectListAdapter: RecyclerView.Adapter<ProjectListAdapter.ProjectHolder>() {

    private var items = mutableListOf<ProjectModel>()

    fun addList(list: List<ProjectModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ProjectHolder(val binding: ItemProjectBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        return ProjectHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_project, parent, false))
    }

    private fun formatDate(date: Long) : String {
        val formattedDate: String
        val c = Calendar.getInstance()
        c.timeInMillis = date
        formattedDate = "${c.get(Calendar.DAY_OF_MONTH)}-${c.get(Calendar.MONTH)}-${c.get(Calendar.YEAR)} ${c.get(
            Calendar.HOUR_OF_DAY)}:${c.get(Calendar.MINUTE)}"
        return formattedDate
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val item = items[position]
        holder.binding.tvName.text = item.projectName
        holder.binding.tvDecription.text = item.projectDesc
        holder.binding.tvDeadline.text = item.projectDeadline
    }
    override fun getItemCount(): Int = items.size
}