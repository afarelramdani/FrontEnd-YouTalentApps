package com.afarelramdani.talentyou.util

import com.afarelramdani.talentyou.model.dataengineer.DataEngineerRepsonse
import com.afarelramdani.talentyou.model.datarecruiter.DataRecruiterResponse
import com.afarelramdani.talentyou.model.login.LoginResponse
import com.afarelramdani.talentyou.model.project.ProjectResponse
import com.afarelramdani.talentyou.model.recruiter.ListEngineerResponse
import com.afarelramdani.talentyou.model.register.RegisterTalentResponse
import retrofit2.http.*

interface ApiService {
    var sharepref: SharedPreferences


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

    @GET("hireApp/engineer")
    suspend fun getAllEngineer(): ListEngineerResponse

    @GET("hireApp/engineer/account/{id}")
    suspend fun getEngineerIdByAccountId(@Path("id") acId: Int? ): DataEngineerRepsonse

    @GET("hireApp/company/account/{id}")
    suspend fun getCompanyIdByAccountId(@Path("id") acId: Int? ): DataRecruiterResponse

    @GET("hireApp/projectByCompany/{companyId}")
    suspend fun getProjectById(@Path("companyId") cnId: Int? ): ProjectResponse


}