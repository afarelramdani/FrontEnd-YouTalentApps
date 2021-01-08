package com.afarelramdani.talentyou.content.portofolio

import com.afarelramdani.talentyou.content.login.LoginResponse
import com.afarelramdani.talentyou.content.projectrecruiter.ProjectResponse
import com.google.gson.annotations.SerializedName

data class PortofolioResponse(val success: Boolean, val message: String, val data: List<Portofolio>) {

    data class Portofolio(@SerializedName("pr_id") val projectId: Int,
                       @SerializedName("en_id") val engineerId: Int,
                       @SerializedName("pr_application") val projectName: String,
                       @SerializedName("pr_desc") val projectDesc: String,
                       @SerializedName("pr_gambar") val projectPicture: String)
}