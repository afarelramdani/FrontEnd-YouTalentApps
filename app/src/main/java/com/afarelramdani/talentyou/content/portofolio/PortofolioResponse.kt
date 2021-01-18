package com.afarelramdani.talentyou.content.portofolio

import com.afarelramdani.talentyou.content.login.LoginResponse
import com.afarelramdani.talentyou.content.projectrecruiter.ProjectResponse
import com.google.gson.annotations.SerializedName

data class PortofolioResponse(val success: Boolean, val message: String, val data: List<Portofolio>) {

    data class Portofolio(@SerializedName("pr_id") val portofolioId: Int,
                       @SerializedName("en_id") val engineerId: Int,
                       @SerializedName("pr_application") val portofolioName: String,
                       @SerializedName("pr_desc") val portofolioDesc: String,
                       @SerializedName("pr_link_pub") val portofolioLinkPub: String,
                       @SerializedName("pr_link_repo") val portofolioLinkRepo: String,
                       @SerializedName("pr_tp_kerja") val portofolioTypeWork: String,
                       @SerializedName("pr_type") val portofolioType: String,
                       @SerializedName("pr_gambar") val portofolioPicture: String)
}