package com.neoguri.neogurinest.api.configuration.security.dto

import com.neoguri.neogurinest.api.application.auth.dto.LoginUserDto
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class AccessTokenAuthentication : AbstractAuthenticationToken {
    private val accessToken: String

    constructor(
        accessToken: String
    ) : super(mutableListOf()) {
        this.accessToken = accessToken
        this.isAuthenticated = false
    }

    constructor(
        accessToken: String,
        userDetail: LoginUserDto,
        authorities: MutableCollection<out GrantedAuthority>?
    ) : super(authorities) {
        this.accessToken = accessToken
        this.details = userDetail
        this.isAuthenticated = true
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getPrincipal(): Any? {
        return accessToken
    }


}