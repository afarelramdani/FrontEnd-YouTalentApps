package com.afarelramdani.talentyou.util

import com.afarelramdani.talentyou.model.dataengineer.DataEngineerRepsonse
import com.afarelramdani.talentyou.model.datarecruiter.DataRecruiterResponse
import com.afarelramdani.talentyou.model.hire.HireResponse
import com.afarelramdani.talentyou.model.hire.UpdateHireResponse
import com.afarelramdani.talentyou.model.login.LoginResponse
import com.afarelramdani.talentyou.model.project.AddProjectResponse
import com.afarelramdani.talentyou.model.project.ProjectResponse
import com.afarelramdani.talentyou.model.recruiter.ListEngineerResponse
import com.afarelramdani.talentyou.model.register.RegisterTalentResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface  ApiService {
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

    @FormUrlEncoded
    @POST( "hireApp/account/createAccount")
    suspend fun registerRecruiter(
        @Field("acName") name: String,
        @Field("acNoHp") noHp: String,
        @Field("acEmail") email: String,
        @Field("acPassword") password: String,
        @Field("cnName") companyName: String,
        @Field("cnPosition") companyPosition: String,
        @Field("acLevel") accountLevel: Int


    ): RegisterTalentResponse

    @GET("hireApp/engineer")
    suspend fun getAllEngineer(): ListEngineerResponse

    @GET("hireApp/engineer/account/{id}")
    suspend fun getEngineerIdByAccountId(@Path("id") acId: Int? ): DataEngineerRepsonse

    @GET("hireApp/company/account/{id}")
    suspend fun getCompanyIdByAccountId(@Path("id") acId: Int? ): DataRecruiterResponse

    @GET("hireApp/projectByCompany/{companyId}")
    suspend fun getProjectById(@Path("companyId") cnId: Int? ): ProjectResponse

    @Multipart
    @POST( "hireApp/project/createProject")
    suspend fun addProject(
        @Part("cnId") companyId: RequestBody,
        @Part("pjName") projectName: RequestBody,
        @Part("pjDesc") projectDesc: RequestBody,
        @Part("pjDeadline") projectDeadline: RequestBody
    ): AddProjectResponse

    @FormUrlEncoded
    @POST("hireApp/hire/createHire")
    suspend fun addHire(
        @Field("enId") engineerId: Int,
        @Field("pjId") projectId: Int,
        @Field("hrPrice") hirePrice: String,
        @Field("hrMessage") hireMessage: String,
        @Field("hrStatus") hireStatus: String
    ) : HireResponse

    @GET("hireApp/hireByEngineer/{enId}")
    suspend fun getHireByEngineerId(@Path("enId") engineerId: Int) : HireResponse

    @PUT("/hireApp/hire/status/{id}")
    @FormUrlEncoded
    suspend fun updateHireStatus(
        @Path("id") id: String,
        @Field("hrStatus") hrStatus: String
    ): UpdateHireResponse

}