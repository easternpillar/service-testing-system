package com.gctoyteam.servicetestingsystem.domain

import javax.persistence.*

@Entity
class Role {
    @Id
    var roleName:String=""

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_authority",
        inverseJoinColumns = [JoinColumn(name = "id")],
    joinColumns = [JoinColumn(name = "role_name")]
    )
    val authorities: MutableSet<Authority> = mutableSetOf()
}