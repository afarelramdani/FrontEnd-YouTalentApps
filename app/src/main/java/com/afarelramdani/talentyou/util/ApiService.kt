package com.afarelramdani.talentyou.util

import com.afarelramdani.talentyou.content.account.ResultResponse
import com.afarelramdani.talentyou.content.experience.ExperienceResponse
import com.afarelramdani.talentyou.content.experience.addexperience.AddExperienceResponse
import com.afarelramdani.talentyou.content.experience.deleteexperience.DeleteExperienceResponse
import com.afarelramdani.talentyou.content.experience.updateexperience.UpdateExperienceResponse
import com.afarelramdani.talentyou.content.home.DataEngineerRepsonse
import com.afarelramdani.talentyou.content.home.DataRecruiterResponse
import com.afarelramdani.talentyou.content.hire.addhire.HireResponse
import com.afarelramdani.talentyou.content.hire.UpdateHireResponse
import com.afarelramdani.talentyou.content.login.LoginResponse
import com.afarelramdani.talentyou.content.portofolio.PortofolioResponse
import com.afarelramdani.talentyou.content.portofolio.deleteportofolio.DeletePortofolioResponse
import com.afarelramdani.talentyou.content.portofolio.updateportofolio.UpdatePortofolioResponse
import com.afarelramdani.talentyou.content.projectrecruiter.addproject.AddProjectResponse
import com.afarelramdani.talentyou.content.projectrecruiter.ProjectResponse
import com.afarelramdani.talentyou.content.projectrecruiter.deleteproject.DeleteProjectResponse
import com.afarelramdani.talentyou.content.projectrecruiter.detailproject.listhire.DetailHireByProjectResponse
import com.afarelramdani.talentyou.content.projectrecruiter.updateproject.UpdateProjectResponse
import com.afarelramdani.talentyou.content.recruiter.editrecruiter.EditRecruiterResponse
import com.afarelramdani.talentyou.content.register.RegisterResponse
import com.afarelramdani.talentyou.content.recruiter.listengineer.ListEngineerResponse
import com.afarelramdani.talentyou.content.skill.SkillResponse
import com.afarelramdani.talentyou.content.skill.addskill.AddSkillResponse
import com.afarelramdani.talentyou.content.skill.addskill.deleteskill.DeleteSkillResponse
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

    @FormUrlEncoded
    @PATCH( "hireApp/account/{id}")
    suspend fun updateAccount(
        @Path("id") Id: Int,
        @Field("ac_name") acName: String,
        @Field("ac_email") acEmail: String,
        @Field("ac_no_hp") acNoHp: String,
    ): ResultResponse

    @PUT("hireApp/account/password/{id}")
    @FormUrlEncoded
    suspend fun updatePasswordAccount(
        @Path("id") id: Int,
        @Field("acPassword") acPassword: String
    ): ResultResponse


    //engineer
    @GET("hireApp/engineer")
    suspend fun getAllEngineer(): ListEngineerResponse

    @GET("hireApp/engineer/account/{id}")
    suspend fun getEngineerIdByAccountId(@Path("id") acId: Int? ): DataEngineerRepsonse

    @GET("hireApp/engineersearch")
    suspend fun getAllEngineerSearch(
        @Query("search") search: String? = null,
        @Query("filter") filter: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = null
    ) : ListEngineerResponse

    @GET("hireApp/company/account/{id}")
    suspend fun getCompanyIdByAccountId(@Path("id") acId: Int? ): DataRecruiterResponse

    @Multipart
    @PATCH("/hireApp/company/{companyId}")
    suspend fun updateCompanyProfile(
        @Path("companyId") cnId: Int,
        @Part("cn_name") companyName: RequestBody,
        @Part("cn_position") companyPosition: RequestBody,
        @Part("cn_part") companyPart: RequestBody,
        @Part("cn_city") companyCity: RequestBody,
        @Part("cn_desc") companyDesc: RequestBody,
        @Part("cn_instagram") companyInstagram: RequestBody,
        @Part("cn_linkedin") companyLinkedin: RequestBody,
    ) : EditRecruiterResponse

    @Multipart
    @PATCH("/hireApp/company/{companyId}")
    suspend fun updateCompanyImageProfile(
        @Path("companyId") cnId: Int,
        @Part image: MultipartBody.Part
    ) : EditRecruiterResponse


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


    @DELETE("hireApp/project/{id}")
    suspend fun deleteProject(@Path("id") Id: Int?) : DeleteProjectResponse

    @GET("hireApp/project/{Id}")
    suspend fun getProjectByIdProject(@Path("Id") cnId: Int? ): ProjectResponse

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

    @GET("hireApp/hire/project/{id}")
    suspend fun getHireByProjectId(@Path("id") engineerId: Int) : DetailHireByProjectResponse

    @Multipart
    @PUT("hireApp/project/{Id}")
    suspend fun updateProject(
        @Path("Id") projectId: Int,
        @Part("cnId") companyId: Int,
        @Part("pjProjectName") projectName: RequestBody,
        @Part("pjDesc") projectDesc: RequestBody,
        @Part("pjDeadline") projectDeadline: RequestBody,
        @Part image: MultipartBody.Part
    ) : UpdateProjectResponse



    @PUT("hireApp/hire/status/{id}")
    @FormUrlEncoded
    suspend fun updateHireStatus(
        @Path("id") id: String,
        @Field("hrStatus") hrStatus: String
    ): UpdateHireResponse

    //Portofolio
    @GET("hireApp/portofolio/engineer/{id}")
    suspend fun getPortofolio(@Path("id") engineerId: Int?) : PortofolioResponse

    @Multipart
    @POST("hireApp/portofolio/createPortofolio")
    suspend fun addPortofolio(
        @Part("enId") engineerId: RequestBody,
        @Part("prApplication") portofolioName: RequestBody,
        @Part("prDesc") portofolioDesc: RequestBody,
        @Part("prLinkPub") portofolioLink: RequestBody,
        @Part("prLinkRepo") portofolioRepo: RequestBody,
        @Part("prTpKerja") portofolioTypeWorking: RequestBody,
        @Part("prType") portofolioType: RequestBody,
        @Part image: MultipartBody.Part
    ) : AddExperienceResponse

    @Multipart
    @PATCH("hireApp/portofolio/{Id}")
    suspend fun updatePortofolio(
        @Path("Id") portofolioId: Int,
        @Part("pr_application") portofolioName: RequestBody,
        @Part("pr_desc") portofolioDesc: RequestBody,
        @Part("pr_link_pub") portofolioLink: RequestBody,
        @Part("pr_link_repo") portofolioRepo: RequestBody,
        @Part("pr_tp_kerja") portofolioTypeWorking: RequestBody,
        @Part("pr_type") portofolioType: RequestBody
    ) : UpdatePortofolioResponse

    @Multipart
    @PATCH("hireApp/portofolio/{Id}")
    suspend fun updateImagePortofolio(
    @Path("Id") portofolioId: Int,
    @Part("pr_application") portofolioName: RequestBody,
    @Part("pr_desc") portofolioDesc: RequestBody,
    @Part("pr_link_pub") portofolioLink: RequestBody,
    @Part("pr_link_repo") portofolioRepo: RequestBody,
    @Part("pr_tp_kerja") portofolioTypeWorking: RequestBody,
    @Part("pr_type") portofolioType: RequestBody,
    @Part image: MultipartBody.Part
    ) : UpdatePortofolioResponse


    @DELETE("/hireApp/portofolio/{id}")
    suspend fun deletePortofolio(@Path("id") Id: Int?) : DeletePortofolioResponse


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

    @DELETE("/hireApp/experience/{id}")
    suspend fun deleteExperience(@Path("id") exId: Int?) : DeleteExperienceResponse

    //skill
    @GET("hireApp/skill/engineer/{enId}")
    suspend fun getSkillByEngineerId(@Path("enId") engineerId: Int) : SkillResponse

    @FormUrlEncoded
    @POST("hireApp/skill/createSkill")
    suspend fun addSkill(
        @Field("en_id") engineerId: Int,
        @Field("sk_name_skill") skillName: String,
    ) : AddSkillResponse

    @DELETE("/hireApp/skill/{id}")
    suspend fun deleteSkill(@Path("id") skId: Int?) : DeleteSkillResponse
}