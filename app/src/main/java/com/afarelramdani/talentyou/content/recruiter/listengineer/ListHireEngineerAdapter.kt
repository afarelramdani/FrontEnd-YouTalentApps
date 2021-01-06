package com.afarelramdani.talentyou.content.listhireengineer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ListEngineerBinding
import com.afarelramdani.talentyou.content.recruiter.listengineer.ListEngineerModel
import com.bumptech.glide.Glide

class ListEngineerAdapter(private val listEngineer : ArrayList<ListEngineerModel>, private val onListEngineerClickListener: OnListEngineerClickListener)
    : RecyclerView.Adapter<ListEngineerAdapter.ListEngineerViewHolder>(){

    fun addList(list: List<ListEngineerModel>) {
        listEngineer.clear()
        listEngineer.addAll(list)
        notifyDataSetChanged()
    }
    class ListEngineerViewHolder(val binding: ListEngineerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListEngineerViewHolder {
        return ListEngineerViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_engineer, parent, false))
    }

    override fun getItemCount(): Int = listEngineer.size

    override fun onBindViewHolder(holder: ListEngineerViewHolder, position: Int) {
        val item = listEngineer[position]

        if (item.engineerProfilePict != null) {
            val img = "http://3.80.117.134:2000/image/${item.engineerProfilePict}"
            Glide.with(holder.itemView)
                .load(img)
                .placeholder(R.drawable.defaultimage)
                .error(R.drawable.defaultimage)
                .into(holder.binding.ivTalent)
        } else {
            val img = R.drawable.defaultimage
            Glide.with(holder.itemView)
                .load(img)
                .placeholder(R.drawable.defaultimage)
                .error(R.drawable.defaultimage)
                .into(holder.binding.ivTalent)
        }


        holder.binding.tvNameTalent.text = item.accountName
        holder.binding.tvJobTittle.text = item.engineerJobTitle


        holder.itemView.setOnClickListener {
            onListEngineerClickListener.onEngineerItem(position)
        }
    }


    interface OnListEngineerClickListener {
        fun onEngineerItem(position : Int)
    }


}