package com.gctoyteam.servicetestingsystem.repository;

import com.gctoyteam.servicetestingsystem.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByMemberId(memberId:String):Member?
}