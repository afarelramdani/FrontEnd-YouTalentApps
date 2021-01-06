package com.afarelramdani.talentyou.model.hire

import com.google.gson.annotations.SerializedName

class UpdateHireResponse(val success: Boolean, val message: String, val data: List<HireResponse.Hire>) {
    data class Hire(
        @SerializedName("hr_id") val hireId: String?,
        @SerializedName("en_id") val engineerId: String?,

    )
}