package com.afarelramdani.talentyou.content.experience

import android.opengl.Visibility
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.portofolio.PortofolioAdapter
import com.afarelramdani.talentyou.content.portofolio.PortofolioModel
import com.afarelramdani.talentyou.databinding.ListExperienceBinding
import com.afarelramdani.talentyou.databinding.ListPortofolioBinding
import com.afarelramdani.talentyou.util.SharedPreferences
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class ExperienceAdapter (private val listExperience: ArrayList<ExperienceModel>, private val onListExperienceClick: onListExperienceClickListener): RecyclerView.Adapter<ExperienceAdapter.ExperienceHolder>() {


    fun addList(list: List<ExperienceModel>) {
        listExperience.clear()
        listExperience.addAll(list)
        notifyDataSetChanged()
    }

    class ExperienceHolder(val binding: ListExperienceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = listExperience.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceHolder {
        return ExperienceHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_experience, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ExperienceHolder, position: Int) {
        val item = listExperience[position]

        val dateStart = item.experienceStart!!.split("T")[0]
        val dateEnd = item.experienceEnd!!.split("T")[0]
        val sum = ChronoUnit.MONTHS.between(LocalDate.parse(dateStart).withDayOfMonth(1), LocalDate.parse(dateEnd).withDayOfMonth(1))

        holder.binding.tvExPosition.text = item.experiencePosition
        holder.binding.tvNameCompany.text = item.experienceCompany
        holder.binding.tvUntilExperience1.text = dateStart
        holder.binding.tvUntilExperience2.text = dateEnd
        holder.binding.tvTimeExperience.text = "$sum Month"
        holder.binding.tvDescExperience.text = item.experienceDesc

        var sharePref = SharedPreferences(holder.itemView.context)

        if (sharePref.getAccountLevel() == 0) {
            holder.binding.btnDelete.visibility = View.GONE
            holder.binding.btnUpdate.visibility = View.GONE
            holder.binding.btnUbahData.visibility = View.GONE
        }

        holder.binding.btnUpdate.setOnClickListener{
            onListExperienceClick.onExperienceEdit(position)
        }
        holder.binding.btnDelete.setOnClickListener{
            onListExperienceClick.onExperienceDelete(position)
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


    interface onListExperienceClickListener  {
        fun onExperienceDelete(position: Int)
        fun onExperienceEdit(position: Int)
    }


}