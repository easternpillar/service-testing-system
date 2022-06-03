package com.gctoyteam.servicetestingsystem.service

import com.gctoyteam.servicetestingsystem.domain.Member
import com.gctoyteam.servicetestingsystem.domain.Role
import com.gctoyteam.servicetestingsystem.dto.MemberDto
import com.gctoyteam.servicetestingsystem.repository.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val memberRepository: MemberRepository):UserDetailsService {
    override fun loadUserByUsername(memberId: String): UserDetails {
        return memberRepository.findByMemberId(memberId)?:throw UsernameNotFoundException("Not Found")
    }

    fun findMemberByMemberId(memberId: String): Member?{
        return memberRepository.findByMemberId(memberId)
    }

    fun registerMember(memberDto:MemberDto): MemberDto?{
        memberRepository.save(Member(
            memberId = memberDto.memberId,
            memberPassword = memberDto.memberPassword,
            memberName = memberDto.memberName,
            roles = memberDto.roles.map { Role().apply { roleName = it } }.toMutableSet()
        ))
        return memberDto
    }
}