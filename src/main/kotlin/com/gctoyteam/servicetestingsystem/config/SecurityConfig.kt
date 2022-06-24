package com.gctoyteam.servicetestingsystem.config

import com.gctoyteam.servicetestingsystem.security.JwtAuthenticationFilter
import com.gctoyteam.servicetestingsystem.security.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(private val jwtTokenProvider: JwtTokenProvider) {
    @Bean
    fun passwordEncoder():BCryptPasswordEncoder=BCryptPasswordEncoder()
    @Bean
    fun filterChain(http:HttpSecurity):SecurityFilterChain{
        return http
                .headers().frameOptions().disable()
            .and()
                .cors()
            .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
//                    .antMatchers("/signup", "/signin").hasAuthority("AccessGuest")
                    .antMatchers("/signup", "/signin").permitAll()
                    .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // hasAuthority("Role_Admin")
//                    .antMatchers("/**").hasAuthority("AccessUser")
                    .anyRequest().authenticated()
            .and()
            .addFilterBefore(JwtAuthenticationFilter(jwtTokenProvider),UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

//    @Bean
//    fun webSecurityCustomizer():WebSecurityCustomizer{
//        return WebSecurityCustomizer { it.ignoring().antMatchers("/signin","signup") }
//    }

}