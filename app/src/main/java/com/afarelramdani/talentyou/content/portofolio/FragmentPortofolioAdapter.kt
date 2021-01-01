package com.afarelramdani.talentyou.content.portofolio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.R

class FragmentPortofolioAdapter: RecyclerView.Adapter<FragmentPortofolioAdapter.PortofolioList>() {
    val listImage = intArrayOf(R.drawable.portofolio1, R.drawable.portofolio2, R.drawable.portofolio3, R.drawable.portofolio2, R.drawable.portofolio3 )


    class PortofolioList(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.iv_portofolio)
    }

    override fun getItemCount(): Int = listImage.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortofolioList {
        return PortofolioList(LayoutInflater.from(parent.context).inflate(R.layout.list_portofolio, parent, false))
    }

    override fun onBindViewHolder(holder: PortofolioList, position: Int) {
        holder.imageView.setImageResource(listImage[position])
    }

}