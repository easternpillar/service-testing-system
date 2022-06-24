package com.gctoyteam.servicetestingsystem.security

import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.util.Collections
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(private val jwtTokenProvider: JwtTokenProvider): GenericFilterBean() {
    private val log=LoggerFactory.getLogger("log")
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req = request as HttpServletRequest
        val res=response as HttpServletResponse
        val token = req.cookies?.associateBy({ it.name }, { it.value })?.get("Authorization")
        log.info(token)

        if (token!=null && jwtTokenProvider.validateToken(token)){
            log.info("토큰 유효")
            SecurityContextHolder.createEmptyContext().authentication=jwtTokenProvider.getAuthentication(token)
            log.info(jwtTokenProvider.getMemberId(token))
        }
        else{
            if (token==null){
                req.setAttribute("UNAUTHORIZATION","토큰 없음")
            }
            else request.setAttribute("UNAUTHORIZATION","토큰 만료")
        }

        chain.doFilter(request,response)
    }
}