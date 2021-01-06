package com.afarelramdani.talentyou.content.home

import com.google.gson.annotations.SerializedName

data class DataRecruiterResponse  (val success: Boolean, val message: String, val data: Data) {
    data class Data(@SerializedName("ac_id") val accountId: String,
                       @SerializedName("ac_name") val accountName: String,
                       @SerializedName("ac_email") val accountEmail: String,
                       @SerializedName("cn_id") val companyId: Int,
                       @SerializedName("cn_foto_profile") val companyFoto: String,


                       )
}