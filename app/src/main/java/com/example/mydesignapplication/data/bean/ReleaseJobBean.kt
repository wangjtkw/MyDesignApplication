package com.example.mydesignapplication.data.bean

data class ReleaseJobBean(
    //releaseJob1
    var positionTitle: String,
    var positionContent: String,
    var personNumber: Int,
    //releaseJob2
    var salary: Int,
    var salaryUnit: String,
    var settlementMethod: String,
    var welfare: ArrayList<String>,
    //releaseJob3
    var workPlace: String,
    var workDate: String,
    var contactInformation: String,//联系方式
    var contactInformationDetail: String,
    var personnelRequirement: PersonnelRequirementBean,
    //releaseJob4
    var industry: String, //行业
    var region: String
)

data class PersonnelRequirementBean(
    var age: String,
    var sex: String,
    var education: String,
    var height: String
)