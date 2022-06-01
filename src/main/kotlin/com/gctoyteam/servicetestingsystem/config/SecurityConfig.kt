package com.gctoyteam.servicetestingsystem.config

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder():PasswordEncoder=BCryptPasswordEncoder()

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
                    .antMatchers("/**").permitAll()
            .and()
            .build()
    }

    @Bean
    fun webSecurityCustomizer():WebSecurityCustomizer{
        return WebSecurityCustomizer { it.ignoring().antMatchers("/**") }
    }

}