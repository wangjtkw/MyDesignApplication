package com.example.mydesignapplication.api

import androidx.lifecycle.LiveData
import com.example.mydesignapplication.data.bean.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import java.io.File


@JvmSuppressWildcards
interface API {

    @GET("employer/register")
    fun employerRegister(
        @Query("employerAccountAccount") employerAccountAccount: String,
        @Query("employerAccountPassword") employerAccountPassword: String
    ): LiveData<ApiResponse<MyResponse<Any>>>

    @GET("employer/login")
    fun employerLogin(
        @Query("employerAccountAccount") employerAccountAccount: String,
        @Query("employerAccountPassword") employerAccountPassword: String
    ): LiveData<ApiResponse<MyResponse<EmployerAccountBean>>>

    @GET("employer/insert/companyInfo")
    fun insertCompanyInfo(
        @Query("accountId") accountId: Int,
        @Query("employerCompanyInfoName") employerCompanyInfoName: String,
        @Query("employerCompanyInfoCompanyType") employerCompanyInfoCompanyType: String,
        @Query("employerCompanyInfoBusinessState") employerCompanyInfoBusinessState: String,
        @Query("employerCompanyInfoFoundTime") employerCompanyInfoFoundTime: String,
        @Query("employerCompanyInfoRegisterAddress") employerCompanyInfoRegisterAddress: String,
        @Query("employerCompanyInfoUniformSocialCreditCode") employerCompanyInfoUniformSocialCreditCode: String,
        @Query("employerCompanyInfoOrganizationCode") employerCompanyInfoOrganizationCode: String,
        @Query("employerCompanyInfoBusinessScope") employerCompanyInfoBusinessScope: String
    ): LiveData<ApiResponse<MyResponse<EmployerAccountBean>>>

    @GET("employer/get/companyInfo")
    fun getCompanyInfo(@Query("companyId") companyId: Int): LiveData<ApiResponse<MyResponse<CompanyInfoBean>>>

    @GET("employer/update/companyInfo")
    fun updateCompanyInfo(
        @Query("employerCompanyInfoId") employerCompanyInfoId: Int,
        @Query("employerCompanyInfoName") employerCompanyInfoName: String,
        @Query("employerCompanyInfoCompanyType") employerCompanyInfoCompanyType: String,
        @Query("employerCompanyInfoBusinessState") employerCompanyInfoBusinessState: String,
        @Query("employerCompanyInfoFoundTime") employerCompanyInfoFoundTime: String,
        @Query("employerCompanyInfoRegisterAddress") employerCompanyInfoRegisterAddress: String,
        @Query("employerCompanyInfoUniformSocialCreditCode") employerCompanyInfoUniformSocialCreditCode: String,
        @Query("employerCompanyInfoOrganizationCode") employerCompanyInfoOrganizationCode: String,
        @Query("employerCompanyInfoBusinessScope") employerCompanyInfoBusinessScope: String
    ): LiveData<ApiResponse<MyResponse<CompanyInfoBean>>>

    @Multipart
    @POST("employer/insert/personalInfo")
    fun insertPersonalInfo(
        @Part headImg: MultipartBody.Part,
        @Part frontImg: MultipartBody.Part,
        @Part backImg: MultipartBody.Part,
        @PartMap params: Map<String, RequestBody>,
    ): LiveData<ApiResponse<MyResponse<EmployerAccountBean>>>

    @GET("employer/get/personalInfo")
    fun getPersonalInfo(@Query("personalInfoId") personalInfoId: Int): LiveData<ApiResponse<MyResponse<PersonalInfoBean>>>

    @POST("employer/update/personalInfo")
    fun updatePersonalInfo(
        @Query("name") name: String,
        @Query("idNum") idNum: String,
        @Query("personalId") personalId: Int
    ): LiveData<ApiResponse<MyResponse<PersonalInfoBean>>>

    @Multipart
    @POST("employer/update/personalInfo/headImg")
    fun updatePersonalInfoHeadImg(
        @Part headImg: MultipartBody.Part,
        @PartMap params: Map<String, RequestBody>,
    ): LiveData<ApiResponse<MyResponse<Any>>>

    @Multipart
    @POST("employer/update/personalInfo/frontImg")
    fun updatePersonalInfoFrontImg(
        @Part frontImg: MultipartBody.Part,
        @PartMap params: Map<String, RequestBody>,
    ): LiveData<ApiResponse<MyResponse<Any>>>

    @Multipart
    @POST("employer/update/personalInfo/backImg")
    fun updatePersonalInfoBackImg(
        @Part backImg: MultipartBody.Part,
        @PartMap params: Map<String, RequestBody>,
    ): LiveData<ApiResponse<MyResponse<Any>>>


    @Multipart
    @POST("employer/insert/positionInfo")
    fun releaseJob(
        @Part positionImg: MultipartBody.Part,
        @PartMap params: Map<String, RequestBody>,
    ): LiveData<ApiResponse<MyResponse<Any>>>

    @GET("employer/get/positionList")
    fun getPositionList(
        @Query("accountId") accountId: Int,
        @Query("status") state: String
    ): LiveData<ApiResponse<MyResponse<List<PositionInfoBean>>>>

    @GET("employer/get/all/records")
    fun getRecords(@Query("employerAccountId") employerAccountId: Int): LiveData<ApiResponse<MyResponse<List<RecordsEntity>>>>

    @GET("employer/get/recordsList/type")
    fun getRecordsType(
        @Query("employerAccountId") employerAccountId: Int,
        @Query("type") type: String
    ): LiveData<ApiResponse<MyResponse<List<RecordInfoResponse>>>>

    @GET("employer/set/giveUp")
    fun setGiveUp(@Query("recordId") recordId: Int): LiveData<ApiResponse<MyResponse<Any>>>

    @GET("employer/set/employ")
    fun setEmploy(@Query("recordId") recordId: Int): LiveData<ApiResponse<MyResponse<Any>>>

    @GET("employer/set/settlement")
    fun setSettlement(@Query("recordId") recordId: Int): LiveData<ApiResponse<MyResponse<Any>>>

    @GET("employer/get/user/info")
    fun getUserInfo(@Query("userAccountId")userAccountId:Int):LiveData<ApiResponse<MyResponse<Users>>>

}