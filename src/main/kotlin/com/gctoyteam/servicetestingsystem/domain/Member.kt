package com.gctoyteam.servicetestingsystem.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name="member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    val id: Long? = null,

    @Column(name="member_id",nullable = false, unique = true)
    val memberId: String = "",

    @Column(name="member_password",nullable = false)
    val memberPassword: String = "",

    @Column(name="member_name",nullable = false)
    val memberName: String = "")
    : UserDetails {
        override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
            return null
        }
        override fun getPassword(): String {
            return memberPassword
        }
        override fun getUsername(): String {
            return memberName
        }

        override fun isAccountNonExpired(): Boolean {
            return true
        }

        override fun isAccountNonLocked(): Boolean {
            return true
        }

        override fun isCredentialsNonExpired(): Boolean {
            return true
        }

        override fun isEnabled(): Boolean {
            return true
        }
    }