package com.afarelramdani.talentyou.model.dataengineer

import com.google.gson.annotations.SerializedName

data class DataEngineerRepsonse (@SerializedName("ac_id") val accountId: String,
                       @SerializedName("ac_name") val accountName: String,
                       @SerializedName("ac_email") val accountEmail: String,
                       @SerializedName("en_id") val engineerId: String)
