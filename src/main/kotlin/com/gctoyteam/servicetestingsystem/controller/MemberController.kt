package com.gctoyteam.servicetestingsystem.controller

import com.gctoyteam.servicetestingsystem.domain.Member
import com.gctoyteam.servicetestingsystem.domain.MemberDto
import com.gctoyteam.servicetestingsystem.repository.MemberRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(private val repository: MemberRepository) {
    @GetMapping("member")
    fun searchByMemberName(@RequestParam memberName:String): MemberDto?{
        return repository.findByMemberName(memberName)?.run{MemberDto(this.memberId,this.memberName)}
    }
    @PostMapping("member")
    fun register(@RequestBody member:Member):MemberDto?{
        return repository.save(member).run { MemberDto(this.memberId,this.memberName) }
    }
}