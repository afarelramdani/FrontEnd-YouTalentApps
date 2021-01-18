package com.afarelramdani.talentyou.content.skill

import com.google.gson.annotations.SerializedName

data class SkillResponse(val success: Boolean, val message: String, val data: List<Data>) {
    data class Data(@SerializedName("sk_id") val skillId: Int,
                       @SerializedName("en_id") val engineerId: Int,
                       @SerializedName("sk_name_skill") val skillName: String)
}