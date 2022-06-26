package com.gctoyteam.servicetestingsystem.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.management.relation.Role
import javax.persistence.*

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    val id: Long? = null,

    @Column(name="member_id",nullable = false, unique = true)
    val memberId: String = "",

    @Column(name="member_password",nullable = false)
    val memberPassword: String = "",

    @Column(name="member_name",nullable = false)
    val memberName: String = "",

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "member_roles",
        inverseJoinColumns = [JoinColumn(name = "role_name")],
        joinColumns = [JoinColumn(name = "id")]
    )
    val roles: MutableSet<com.gctoyteam.servicetestingsystem.domain.Role> = mutableSetOf(),

    ): UserDetails {
        override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
            return roles.flatMap { it.authorities }.map { SimpleGrantedAuthority(it.authorityName)}.toMutableSet()
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