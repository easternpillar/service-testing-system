package com.gctoyteam.servicetestingsystem.service

import com.gctoyteam.servicetestingsystem.domain.Member
import com.gctoyteam.servicetestingsystem.domain.MemberDto
import com.gctoyteam.servicetestingsystem.repository.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val memberRepository: MemberRepository):UserDetailsService {
    override fun loadUserByUsername(memberId: String): UserDetails? {
        return memberRepository.findByMemberId(memberId)
    }

    fun findMemberByMemberId(memberId: String): Member?{
        return memberRepository.findByMemberId(memberId)
    }

    fun registerMember(member:Member):MemberDto?{
        val save = memberRepository.save(member)
        return MemberDto(save.memberPassword,save.memberName)
    }
}