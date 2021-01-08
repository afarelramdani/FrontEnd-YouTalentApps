package com.afarelramdani.talentyou.content.portofolio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ListPortofolioBinding
import com.afarelramdani.talentyou.util.SharedPreferences
import com.bumptech.glide.Glide

class PortofolioAdapter(private val listPortofolio: ArrayList<PortofolioModel>, private val PortofolioClick:  onListPortofolioClickListener): RecyclerView.Adapter<PortofolioAdapter.PortofolioHolder>() {

    fun addList(list: List<PortofolioModel>) {
        listPortofolio.clear()
        listPortofolio.addAll(list)
        notifyDataSetChanged()
    }

    class PortofolioHolder(val binding: ListPortofolioBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = listPortofolio.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortofolioHolder {
        return PortofolioHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_portofolio, parent, false))
    }

    override fun onBindViewHolder(holder: PortofolioHolder, position: Int) {
        var sharePref = SharedPreferences(holder.itemView.context)

        val item = listPortofolio[position]

        if (item.projectGambar != null) {
            val img = "http://3.80.117.134:2000/image/${item.projectGambar}"
            Glide.with(holder.itemView)
                .load(img)
                .placeholder(R.drawable.defaultimage)
                .error(R.drawable.defaultimage)
                .into(holder.binding.ivPortofolio)
        } else {
            val img = R.drawable.defaultimage
            Glide.with(holder.itemView)
                .load(img)
                .placeholder(R.drawable.defaultimage)
                .error(R.drawable.defaultimage)
                .into(holder.binding.ivPortofolio)
        }


    }

    interface onListPortofolioClickListener  {
        fun onHireDelete(position: Int)
        fun onHireEdit(position: Int)
    }

}