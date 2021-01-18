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

class PortofolioAdapter(private val listPortofolio: ArrayList<PortofolioModel>, private val onListPortofolioClick:  onListPortofolioClickListener): RecyclerView.Adapter<PortofolioAdapter.PortofolioHolder>() {

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

        if (sharePref.getAccountLevel() == 0) {
            holder.binding.btnDelete.visibility = View.GONE
            holder.binding.btnUpdate.visibility = View.GONE
            holder.binding.btnUbahData.visibility = View.GONE
        }

        val item = listPortofolio[position]

        if (item.prImage!= null) {
            val img = "http://3.80.117.134:2000/image/${item.prImage}"
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

        holder.binding.tvPrDesc.text = item.prDesc
        holder.binding.tvPortofolioName.text = item.prName

        holder.binding.btnUpdate.setOnClickListener{
            onListPortofolioClick.onPortofolioEdit(position)
        }
        holder.binding.btnDelete.setOnClickListener{
            onListPortofolioClick.onPortofolioDelete(position)
        }

        holder.binding.btnUbahData.setOnClickListener{
            holder.binding.btnUpdate.visibility = View.VISIBLE
            holder.binding.btnDelete.visibility = View.VISIBLE
            holder.binding.btnCancel.visibility = View.VISIBLE
            holder.binding.btnUbahData.visibility = View.GONE
        }

        holder.binding.btnCancel.setOnClickListener{
            holder.binding.btnUpdate.visibility = View.GONE
            holder.binding.btnDelete.visibility = View.GONE
            holder.binding.btnCancel.visibility = View.GONE
            holder.binding.btnUbahData.visibility = View.VISIBLE
        }

    }



    interface onListPortofolioClickListener  {
        fun onPortofolioDelete(position: Int)
        fun onPortofolioEdit(position: Int)
    }

}