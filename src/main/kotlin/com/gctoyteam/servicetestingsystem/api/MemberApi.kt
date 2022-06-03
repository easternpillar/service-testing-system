package com.gctoyteam.servicetestingsystem.api

import com.gctoyteam.servicetestingsystem.dto.LoginDto
import com.gctoyteam.servicetestingsystem.domain.Member
import com.gctoyteam.servicetestingsystem.dto.MemberDto
import com.gctoyteam.servicetestingsystem.service.CustomUserDetailsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberApi(private val customUserDetailsService: CustomUserDetailsService) {
    @PostMapping("signup")
    fun signUp(@RequestBody memberDto:MemberDto):ResponseEntity<MemberDto>?{
        val findMember = customUserDetailsService.findMemberByMemberId(memberDto.memberId)
        return if (findMember!=null)
            ResponseEntity(HttpStatus.CONFLICT)
        else ResponseEntity.ok(customUserDetailsService.registerMember(memberDto))
    }

    @GetMapping("member")
    fun findMember(loginDto: LoginDto):Member?{
        return customUserDetailsService.findMemberByMemberId(loginDto.memberId)
    }
}