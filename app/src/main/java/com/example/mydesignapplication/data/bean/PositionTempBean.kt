package com.example.mydesignapplication.data.bean

import androidx.lifecycle.MutableLiveData
import com.example.mydesignapplication.ui.album.AlbumBean

object PositionTempBean {
    var employerPositionTitle = MutableLiveData<String>("akjsdlasjld")
    var employerPositionContent = MutableLiveData<String>("asdasdas")
    var employerPositionPersonNum = MutableLiveData<String>("asdasds")
    var employerPositionImg = MutableLiveData<AlbumBean>()

    var employerPositionSalary = MutableLiveData<String>()
    var employerPositionSalaryUnit = MutableLiveData<String>()
    var employerPositionSalaryUnitIndex = 0
    var employerPositionSettlement = MutableLiveData<String>()
    var employerPositionSettlementIndex = MutableLiveData<Int>(0)
    var employerPositionWelfare = MutableLiveData<String>("包吃￥包住￥交通补贴")

    var employerPositionPlace = MutableLiveData<String>()
    var employerPositionDate = MutableLiveData<String>("请选择")
    var employerPositionDateIndex = 0
    var employerPositionConnectType = MutableLiveData<String>("请选择")
    var employerPositionConnectTypeIndex = 0
    var employerPositionConnectInfo = MutableLiveData<String>()
    var isNeedPersonRequirement = MutableLiveData<Boolean>(false)
    var employerPositionPersonalRequirement = MutableLiveData<String>("a")

    var positionRequirementAge = MutableLiveData<String>("")
    var positionRequirementSex = MutableLiveData<String>("")
    var positionRequirementHeight = MutableLiveData<String>("")
    var positionRequirementEducation = MutableLiveData<String>("")

    var employerPositionIndustry = MutableLiveData<String>("其他")
    var employerPositionCity = MutableLiveData<String>("重庆")
//    var employerPositionIndustry = MutableLiveData<String>("请选择")
//    var employerPositionCity = MutableLiveData<String>("请选择")

    var employerAccountId = MutableLiveData<Int>()
}