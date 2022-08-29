package com.neoguri.neogurinest.api.configuration.security.dto

import com.neoguri.neogurinest.api.configuration.security.role.AnonymousRole
import org.springframework.security.authentication.AbstractAuthenticationToken

class AnonymousAuthentication : AbstractAuthenticationToken(mutableListOf(AnonymousRole())) {

    init {
        this.isAuthenticated = true
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getPrincipal(): Any? {
        return null
    }

}