package com.example.mydesignapplication.data.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "company_info_bean")
@JsonClass(generateAdapter = true)
data class CompanyInfoBean(
    @PrimaryKey
    @Json(name = "employerCompanyInfoId")
    val employerCompanyInfoId: Int,
    @Json(name = "employerCompanyInfoAuditState")
    val employerCompanyInfoAuditState: String,
    @Json(name = "employerCompanyInfoBusinessScope")
    val employerCompanyInfoBusinessScope: String,
    @Json(name = "employerCompanyInfoBusinessState")
    val employerCompanyInfoBusinessState: String,
    @Json(name = "employerCompanyInfoCompanyType")
    val employerCompanyInfoCompanyType: String,
    @Json(name = "employerCompanyInfoFoundTime")
    val employerCompanyInfoFoundTime: String,
    @Json(name = "employerCompanyInfoOrganizationCode")
    val employerCompanyInfoOrganizationCode: String,
    @Json(name = "employerCompanyInfoRegisterAddress")
    val employerCompanyInfoRegisterAddress: String,
    @Json(name = "employerCompanyInfoUniformSocialCreditCode")
    val employerCompanyInfoUniformSocialCreditCode: String
)