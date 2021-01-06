package com.afarelramdani.talentyou.content.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(val success: Boolean, val message: String, val data: AccountItem) {
    data class AccountItem(
        @SerializedName("ac_id")
        val acId: Int,

        @SerializedName("ac_email")
        val acEmail: String,

        @SerializedName("ac_level")
        val acLevel: Int,

        @SerializedName("ac_name")
        val acName: String,

        @SerializedName("token")
        val token: String
    )
}