package com.afarelramdani.talentyou.content.projectrecruiter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ItemProjectBinding
import com.bumptech.glide.Glide
import java.util.*

class ProjectListAdapter(private val listProject: ArrayList<ProjectModel>, private val onListProjectClickListener: OnListProjectClickListener): RecyclerView.Adapter<ProjectListAdapter.ProjectHolder>() {

    fun addList(list: List<ProjectModel>) {
        listProject.clear()
        listProject.addAll(list)
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
        val item = listProject[position]
        val img = "http://3.80.117.134:2000/image/${item.projectPicture}"

        holder.binding.tvProjectName.text = item.projectName
        holder.binding.tvProjectDeadline.text = item.projectDeadline!!.split("T")[0]
        holder.binding.tvDesc.text = item.projectDesc

        Glide.with(holder.itemView)
            .load(img)
            .placeholder(R.drawable.projectdefault)
            .error(R.drawable.projectdefault)
            .into(holder.binding.ivProject)

        holder.binding.btnDetailProject.setOnClickListener {
            onListProjectClickListener.onProjectItemClicked(position)
        }

    }
    override fun getItemCount(): Int = listProject.size

    interface OnListProjectClickListener {
        fun onProjectItemClicked(position : Int)
    }

}