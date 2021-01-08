package com.afarelramdani.talentyou.util

import com.afarelramdani.talentyou.content.experience.ExperienceResponse
import com.afarelramdani.talentyou.content.experience.addexperience.AddExperienceResponse
import com.afarelramdani.talentyou.content.experience.updateexperience.UpdateExperienceResponse
import com.afarelramdani.talentyou.content.home.DataEngineerRepsonse
import com.afarelramdani.talentyou.content.home.DataRecruiterResponse
import com.afarelramdani.talentyou.content.hire.HireResponse
import com.afarelramdani.talentyou.content.talent.UpdateHireResponse
import com.afarelramdani.talentyou.content.login.LoginResponse
import com.afarelramdani.talentyou.content.portofolio.PortofolioResponse
import com.afarelramdani.talentyou.content.projectrecruiter.addproject.AddProjectResponse
import com.afarelramdani.talentyou.content.projectrecruiter.ProjectResponse
import com.afarelramdani.talentyou.content.register.RegisterResponse
import com.afarelramdani.talentyou.content.recruiter.listengineer.ListEngineerResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface  ApiService {



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
      @Field("acEmail") email: String,
      @Field("acNoHp") noHp: String,
      @Field("acPassword") password: String,
      @Field("acLevel") accountLevel: Int,
    ): RegisterResponse

    @FormUrlEncoded
    @POST( "hireApp/account/createAccount")
    suspend fun registerRecruiter(
        @Field("acName") name: String,
        @Field("acNoHp") noHp: String,
        @Field("acEmail") email: String,
        @Field("acPassword") password: String,
        @Field("acLevel") accountLevel: Int,
        @Field("cnName") companyName: String,
        @Field("cnPosition") companyPosition: String,
    ): RegisterResponse

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
        @Part("pjDeadline") projectDeadline: RequestBody,
        @Part image: MultipartBody.Part
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

    @PUT("hireApp/hire/status/{id}")
    @FormUrlEncoded
    suspend fun updateHireStatus(
        @Path("id") id: String,
        @Field("hrStatus") hrStatus: String
    ): UpdateHireResponse

    @GET("hireApp/portofolio/engineer/{id}")
    suspend fun getPortofolio(@Path("id") engineerId: Int?) : PortofolioResponse

    //EXPERIENCE
    @GET("hireApp/experience/engineer/{id}")
    suspend fun getExperience(@Path("id") engineerId: Int?) : ExperienceResponse

    @FormUrlEncoded
    @POST("hireApp/experience/createExperience")
    suspend fun addExperience(
        @Field("enId") engineerId: Int?,
        @Field("exPosition") exPosition: String?,
        @Field("exCompany") exCompany: String?,
        @Field("exStart") expStart: String?,
        @Field("exEnd") expEnd: String?,
        @Field("exDesc") expDesc: String?
    ) : AddExperienceResponse

    @FormUrlEncoded
    @PUT("hireApp/experience/{id}")
    suspend fun updateExperience(
        @Path("id") exId: Int?,
        @Field("ex_position") exPosition: String?,
        @Field("ex_company") exCompany: String?,
        @Field("ex_start") exStart: String?,
        @Field("ex_end") exEnd: String?,
        @Field("ex_desc") exDesc: String?
    ) : UpdateExperienceResponse

}