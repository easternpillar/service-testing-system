package com.gctoyteam.servicetestingsystem.service

import com.gctoyteam.servicetestingsystem.domain.Member
import com.gctoyteam.servicetestingsystem.domain.Role
import com.gctoyteam.servicetestingsystem.dto.LoginDto
import com.gctoyteam.servicetestingsystem.dto.RegisterDto
import com.gctoyteam.servicetestingsystem.repository.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.math.log

@Service
@Transactional
class CustomUserDetailsService(private val memberRepository: MemberRepository, private val passwordEncoder: BCryptPasswordEncoder):UserDetailsService {
    @Transactional(readOnly = true)
    override fun loadUserByUsername(memberId: String): UserDetails? {
        return memberRepository.findByMemberId(memberId)
    }

    fun registerMember(registerDto:RegisterDto): RegisterDto?{
        memberRepository.save(Member(
            memberId = registerDto.memberId,
            memberPassword = passwordEncoder.encode(registerDto.memberPassword),
            memberName = registerDto.memberName,
            roles = registerDto.roles.map { Role().apply { roleName = it } }.toMutableSet()
        ))
        return registerDto
    }

    fun loginMember(loginDto: LoginDto): Boolean {
        val findMember = memberRepository.findByMemberId(loginDto.memberId) ?: return false
        return passwordEncoder.matches(loginDto.memberPassword,findMember.memberPassword)
    }

    fun findMembers():List<Member>{
        return memberRepository.findAll()
    }
}