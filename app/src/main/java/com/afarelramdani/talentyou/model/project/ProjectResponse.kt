package com.afarelramdani.talentyou.model.project

import com.google.gson.annotations.SerializedName

data class ProjectResponse(val succes: Boolean, val message: String, val data: List<Project>) {
    data class Project(@SerializedName("pj_id") val projectId: String,
                       @SerializedName("cn_id") val companyId: String,
                       @SerializedName("pj_project_name") val projectName: String,
                       @SerializedName("pj_desc") val projectDesc: String,
                       @SerializedName("pj_picture") val projectPicture: String,
                       @SerializedName("pj_deadline") val projectDeadline: String)
}