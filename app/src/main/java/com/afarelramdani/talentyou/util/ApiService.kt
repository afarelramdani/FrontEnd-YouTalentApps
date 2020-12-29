package com.afarelramdani.talentyou.util

import com.afarelramdani.talentyou.model.login.LoginResponse
import com.afarelramdani.talentyou.model.project.ProjectResponse
import com.afarelramdani.talentyou.model.register.RegisterTalentResponse
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

    @FormUrlEncoded
    @POST( "hireApp/account/createAccount")
    suspend fun registerTalent(
      @Field("acName") name: String,
      @Field("acNoHp") noHp: String,
      @Field("acEmail") email: String,
      @Field("acPassword") password: String,
      @Field("acLevel") accountLevel: Int,
    ): RegisterTalentResponse


}