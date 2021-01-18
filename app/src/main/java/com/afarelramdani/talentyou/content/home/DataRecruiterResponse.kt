package com.afarelramdani.talentyou.content.home

import com.google.gson.annotations.SerializedName

data class DataRecruiterResponse  (val success: Boolean, val message: String, val data: Data) {
    data class Data(@SerializedName("ac_id") val accountId: String,
                       @SerializedName("ac_name") val accountName: String,
                       @SerializedName("ac_email") val accountEmail: String,
                       @SerializedName("ac_no_hp") val accountNoHp: String,
                       @SerializedName("cn_name") val companyName: String,
                       @SerializedName("cn_position") val companyPosition: String,
                       @SerializedName("cn_part") val companyPart: String,
                       @SerializedName("cn_city") val companyLocation: String,
                       @SerializedName("cn_desc") val companyDesc: String,
                       @SerializedName("cn_instagram") val companyInstagram: String,
                       @SerializedName("cn_linkedin") val companyLinkedin: String,
                       @SerializedName("cn_id") val companyId: Int,
                       @SerializedName("cn_foto_profile") val companyFoto: String,
                       )
}
