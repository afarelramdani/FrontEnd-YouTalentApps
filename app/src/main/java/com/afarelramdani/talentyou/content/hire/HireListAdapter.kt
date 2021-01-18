package com.afarelramdani.talentyou.content.hire

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ListHireBinding
import com.bumptech.glide.Glide

class HireListAdapter(private val listHire: ArrayList<HireModel>, private val onListHireClickListener: clickOnListHireListener): RecyclerView.Adapter<HireListAdapter.HireHolder>() {


    fun addList(list: List<HireModel>) {
        listHire.clear()
        listHire.addAll(list)
        notifyDataSetChanged()
    }

    class HireHolder(val binding: ListHireBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HireHolder {
        return HireHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_hire, parent, false))
    }

    override fun getItemCount(): Int = listHire.size

    override fun onBindViewHolder(holder: HireHolder, i: Int) {
        val item = listHire[i]
        val img = "http://3.80.117.134:2000/image/${item.hireProjectImage}"

        holder.binding.tvHrPrice.text = item.hirePrice.toString()
        holder.binding.tvStatus.text = item.hireStatus
        holder.binding.tvProjectName.text = item.projectName
        holder.binding.tvProjectDeadline.text = item.projectDeadline!!.split("T")[0]

        Glide.with(holder.itemView)
            .load(img)
            .placeholder(R.drawable.projectdefault)
            .error(R.drawable.projectdefault)
            .into(holder.binding.ivProjectList)


        holder.binding.btnDetail.setOnClickListener {
            onListHireClickListener.detailProjectClick(i)
        }
    }



    interface clickOnListHireListener {
        fun detailProjectClick(position : Int)
    }

}