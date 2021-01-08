package com.afarelramdani.talentyou.content.experience

import com.google.gson.annotations.SerializedName

data class ExperienceResponse(val success: Boolean, val message: String, val data: List<Experience>) {
    data class Experience(@SerializedName("ex_id") val experienceId: Int,
                          @SerializedName("en_id") val engineerId: Int,
                          @SerializedName("ex_position") val exPosition: String,
                          @SerializedName("ex_company") val experienceCompany: String,
                          @SerializedName("ex_start") val experienceStart: String,
                          @SerializedName("ex_end") val experienceEnd: String,
                          @SerializedName("ex_desc") val experienceDesc: String


    )
}