package com.gctoyteam.servicetestingsystem.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "member")
open class Member: UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "member_id", nullable = false, unique = true)
    open var memberId: String? = null

    @Column(name = "member_password", nullable = false)
    open var memberPassword: String? = null

    @Column(name = "member_name", nullable = false)
    open var memberName: String? = null
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("Not yet implemented")
    }

    override fun getPassword(): String {
        TODO("Not yet implemented")
    }

    override fun getUsername(): String {
        TODO("Not yet implemented")
    }

    override fun isAccountNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isAccountNonLocked(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isCredentialsNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnabled(): Boolean {
        TODO("Not yet implemented")
    }
}