package com.gctoyteam.servicetestingsystem.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority


class JwtAuthentication : UsernamePasswordAuthenticationToken {
    constructor(principal: String?, credentials: String?) : super(principal, credentials)
    constructor(
        principal: String?, credentials: String?,
        authorities: MutableCollection<out GrantedAuthority>?
    ) : super(principal, credentials, authorities)
}