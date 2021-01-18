package com.afarelramdani.talentyou.content.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.listhireengineer.ListEngineerAdapter
import com.afarelramdani.talentyou.content.recruiter.listengineer.ListEngineerModel
import com.afarelramdani.talentyou.databinding.ListEngineerBinding
import com.bumptech.glide.Glide

class SearchAdapter(private val listEngineer: ArrayList<ListEngineerModel>, private val onListEngineerClickListener: OnListEngineerClickListener) : RecyclerView.Adapter<SearchAdapter.ListEngineerHolder>() {

    fun addList(list: List<ListEngineerModel>) {
        listEngineer.clear()
        listEngineer.addAll(list)
        notifyDataSetChanged()
    }

    class ListEngineerHolder(val binding: ListEngineerBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListEngineerHolder {
        return ListEngineerHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(
                    parent.context
                ), R.layout.list_engineer, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ListEngineerHolder, position: Int) {
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


    override fun getItemCount(): Int = listEngineer.size

    interface OnListEngineerClickListener {
        fun onEngineerItem(position : Int)
    }
}