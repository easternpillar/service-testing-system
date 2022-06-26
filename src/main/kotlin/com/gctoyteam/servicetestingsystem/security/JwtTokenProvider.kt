package com.gctoyteam.servicetestingsystem.security

import com.gctoyteam.servicetestingsystem.service.CustomUserDetailsService
import io.jsonwebtoken.Jwt
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.crypto.SecretKey

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
        val claims= Jwts.claims().setSubject("auth")
        val now=Date()
        return Jwts.builder().setHeaderParam("typ","JWT").setSubject(memberId).claim("roles","ROLE_ADMIN").setIssuedAt(now).setExpiration(Date(expTime+now.time)).signWith(secretKey).compact()
    }

    fun getMemberId(token:String):String{
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject
    }
    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
        }catch (ex: java.lang.Exception){
            return false
        }
        return true
    }

    fun getAuthentication(token: String?): JwtAuthentication {
        if (token!=null) {
            val parsed = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
            return JwtAuthentication(getMemberId(token), null, mutableSetOf<GrantedAuthority>().also {
                it.add(SimpleGrantedAuthority(parsed.body["roles"].toString()))
            })
        } else{
            return JwtAuthentication("GUEST",null, mutableSetOf<GrantedAuthority>().apply { this.add(SimpleGrantedAuthority("GUEST")) })
        }
    }
}