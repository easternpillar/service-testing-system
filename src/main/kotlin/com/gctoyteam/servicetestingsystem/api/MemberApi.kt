package com.gctoyteam.servicetestingsystem.api

import com.gctoyteam.servicetestingsystem.domain.Member
import com.gctoyteam.servicetestingsystem.dto.LoginDto
import com.gctoyteam.servicetestingsystem.dto.RegisterDto
import com.gctoyteam.servicetestingsystem.security.JwtTokenProvider
import com.gctoyteam.servicetestingsystem.service.CustomUserDetailsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
class MemberApi(private val customUserDetailsService: CustomUserDetailsService, private val jwtTokenProvider: JwtTokenProvider) {
    @PostMapping("/signup")
    fun signUp(@RequestBody registerDto: RegisterDto):ResponseEntity<RegisterDto>?{
        val findMember = customUserDetailsService.loadUserByUsername(registerDto.memberId)
        return if (findMember!=null)
            ResponseEntity(HttpStatus.CONFLICT)
        else{
            customUserDetailsService.registerMember(registerDto)
            ResponseEntity.ok().build()
        }
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody loginDto: LoginDto, httpServletResponse: HttpServletResponse) {
        if (customUserDetailsService.loginMember(loginDto)){
            httpServletResponse.addCookie(Cookie("Authorization",jwtTokenProvider.createToken(loginDto.memberId))
                .apply {
                    isHttpOnly=true
            })
            httpServletResponse.status=HttpStatus.OK.value()
        }else httpServletResponse.status=HttpStatus.BAD_REQUEST.value()
    }

    @GetMapping("/members")
    fun findMembers(): List<Member> {
        return customUserDetailsService.findMembers()
    }
}