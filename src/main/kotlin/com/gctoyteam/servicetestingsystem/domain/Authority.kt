package com.gctoyteam.servicetestingsystem.domain

import javax.persistence.*

@Entity
class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    val id: Long? = null

    val authorityName: String?=""
}