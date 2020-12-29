package com.afarelramdani.talentyou.model.recruiter

import com.google.gson.annotations.SerializedName

data class listEngineerResponse (val succes: String, val message: String, val data: List<Project?>) {
        data class Project(@SerializedName("en_id") val engineerId: Int,
                           @SerializedName("ac_id") val accountId: Int,
                           @SerializedName("en_job_tittle") val engineerJobTittle: String,
                           @SerializedName("en_job_type") val engineerJobType: String,
                           @SerializedName("en_origin") val engineerOrigin: String,
                           @SerializedName("en_desc") val engineerDesc: String,
                           @SerializedName("en_foto_profile") val engineerFotoProfile: String,
                           @SerializedName("en_created_at") val createdAt: String,
                           @SerializedName("ac_name") val accountName: String,
                           @SerializedName("ac_email") val accountEmail: String,
                           @SerializedName("ac_no_hp") val accountNoHp: String)
    }
