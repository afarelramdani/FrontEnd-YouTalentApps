package com.afarelramdani.talentyou.content.projectrecruiter.detailproject.listhire

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ListHireByProjectBinding
import com.bumptech.glide.Glide

class ListHireByProjectRecyclerViewAdapter(private val listHire: ArrayList<DetailHireByProjectModel>) :
    RecyclerView.Adapter<ListHireByProjectRecyclerViewAdapter.HireListHolder>() {

    fun addList(list: List<DetailHireByProjectModel>) {
        listHire.clear()
        listHire.addAll(list)
        notifyDataSetChanged()
    }

    class HireListHolder(val binding: ListHireByProjectBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HireListHolder {
        return HireListHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_hire_by_project,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listHire.size

    override fun onBindViewHolder(holder: HireListHolder, position: Int) {
        val item = listHire[position]
        val img = "http://3.80.117.134:2000/image/${item.engineerPhoto}"

        holder.binding.tvNameTalent.text = item.engineerName
        holder.binding.tvPrice.text = item.hirePrice.toString()
        holder.binding.tvStatus.text = item.hireStatus
        holder.binding.tvTypeTalent.text = item.engineerJobTitle

        if(item.hireStatus == "approve") {
            holder.binding.tvStatus.setTextColor(Color.rgb(0, 122, 0))
        } else if (item.hireStatus == "reject") {
            holder.binding.tvStatus.setTextColor(Color.rgb(122, 0, 0))
        }

        Glide.with(holder.itemView)
            .load(img)
            .placeholder(R.drawable.defaultimage)
            .error(R.drawable.defaultimage)
            .into(holder.binding.ivTalent)
    }

}