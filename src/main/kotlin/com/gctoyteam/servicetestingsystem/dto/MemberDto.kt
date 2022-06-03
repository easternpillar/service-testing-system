package com.gctoyteam.servicetestingsystem.dto

import java.io.Serializable

data class MemberDto(var memberId: String, var memberPassword: String, var memberName: String, var roles:List<String>) : Serializable{
}
