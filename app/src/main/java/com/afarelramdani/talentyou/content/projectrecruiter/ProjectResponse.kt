package com.afarelramdani.talentyou.content.projectrecruiter

import com.google.gson.annotations.SerializedName

data class ProjectResponse(val success: Boolean, val message: String, val data: List<Project>) {
    data class Project(@SerializedName("pj_id") val projectId: Int,
                       @SerializedName("cn_id") val companyId: String,
                       @SerializedName("pj_project_name") val projectName: String,
                       @SerializedName("pj_desc") val projectDesc: String,
                       @SerializedName("pj_picture") val projectPicture: String,
                       @SerializedName("pj_deadline") val projectDeadline: String)
}