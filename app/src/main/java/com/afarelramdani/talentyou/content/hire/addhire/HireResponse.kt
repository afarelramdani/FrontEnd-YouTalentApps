package com.afarelramdani.talentyou.content.hire.addhire

import com.google.gson.annotations.SerializedName

data class HireResponse(val success: Boolean, val message: String, val data: List<Hire>) {
    data class Hire(
        @SerializedName("hr_id") val hireId: String?,
        @SerializedName("en_id") val engineerId: String?,
        @SerializedName("pj_id") val projectId: String?,
        @SerializedName("hr_price") val hirePrice: String?,
        @SerializedName("hr_message") val hireMessage: String?,
        @SerializedName("hr_status") val hireStatus: String?,
        @SerializedName("hr_date_confirm") val hireDateConfirm: String?,
        @SerializedName("pj_picture") val projectPicture: String?,
        @SerializedName("pj_project_name") val projectName: String?,
        @SerializedName("pj_deadline") val projectDeadline: String?,
        @SerializedName("hr_created_at") val hireCreated: String?
    )
}