package com.gctoyteam.servicetestingsystem.domain

import java.io.Serializable

data class MemberDto(var memberId: String? = null, var memberName: String? = null) : Serializable{
    constructor(member:Member) : this(){
        this.memberId=member.memberId
        this.memberName=member.memberName
    }
}
