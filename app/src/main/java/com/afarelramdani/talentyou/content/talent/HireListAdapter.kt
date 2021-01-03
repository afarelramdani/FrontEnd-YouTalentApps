package com.afarelramdani.talentyou.content.talent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ListHireBinding
import com.afarelramdani.talentyou.model.hire.HireModel

class HireListAdapter: RecyclerView.Adapter<HireListAdapter.HireHolder>() {

    private var items = mutableListOf<HireModel>()

    fun addList(list: List<HireModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class HireHolder(val binding: ListHireBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HireHolder {
        return HireHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_hire, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HireHolder, i: Int) {
        val item = items[i]
        holder.binding.tvMessage.text = item.hireMessage
        holder.binding.tvHrPrice.text = item.hirePrice.toString()


    }
}