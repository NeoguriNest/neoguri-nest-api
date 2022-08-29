package com.neoguri.neogurinest.api.configuration.security.provider

import com.neoguri.neogurinest.api.configuration.security.dto.AnonymousAuthentication
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class AnonymousAuthenticationProvider : AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        return authentication as AnonymousAuthentication
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return AnonymousAuthentication::class.java.isAssignableFrom(authentication!!)
    }
}