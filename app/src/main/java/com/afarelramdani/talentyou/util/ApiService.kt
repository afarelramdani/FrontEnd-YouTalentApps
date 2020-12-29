package com.afarelramdani.talentyou.util

import com.afarelramdani.talentyou.model.login.LoginResponse
import com.afarelramdani.talentyou.model.project.ProjectResponse
import retrofit2.http.*

interface ApiService {
    @GET("hireApp/project")
    suspend fun getProjectById(): ProjectResponse

    @FormUrlEncoded
    @POST("hireApp/login")
    suspend fun loginAccount(
        @Field("acEmail") email: String,
        @Field("acPassword") password: String
    ): LoginResponse
}