package com.afarelramdani.talentyou.model.dataengineer

import com.google.gson.annotations.SerializedName

data class DataEngineerRepsonse (val success: Boolean, val message: String, val data: Data) {
    data class Data(
    @SerializedName("ac_id") val accountId: String,
    @SerializedName("ac_name") val accountName: String,
    @SerializedName("ac_email") val accountEmail: String,
    @SerializedName("en_id") val engineerId: Int,
    @SerializedName("en_foto_profile") val engineerPhoto: String,
    @SerializedName("en_job_tittle") val engineerJobTitle: String)
}
