package com.gctoyteam.servicetestingsystem.security

import com.gctoyteam.servicetestingsystem.service.CustomUserDetailsService
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.annotation.PostConstruct
import javax.crypto.SecretKey
import kotlin.math.exp

@Component
class JwtTokenProvider() {
    private val expTime=1000L*60*60
    @Value(value = "\${secretKey}")
    private var key = ""
    private var secretKey : SecretKey? = null

    @PostConstruct
    fun init(){
        secretKey = Keys.hmacShaKeyFor(key.toByteArray())
    }

    fun createToken(memberId:String):String{
        val claims= Jwts.claims().setSubject(memberId)
        val now=Date()
        return Jwts.builder().setHeaderParam("typ","JWT").setClaims(claims).setIssuedAt(now).setExpiration(Date(expTime+now.time)).signWith(secretKey).compact()
    }
}