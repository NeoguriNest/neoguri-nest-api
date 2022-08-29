package com.neoguri.neogurinest.api.configuration.security.filter

import com.neoguri.neogurinest.api.configuration.security.entrypoint.NeoguriAuthenticationEntryPoint
import com.neoguri.neogurinest.api.domain.auth.exception.InvalidAccessTokenException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RequireLoginFilter(override val authenticationEntryPoint: AuthenticationEntryPoint) : AbstractNeoguriAuthenticationFilter(
    RequestMatcher {
        it.requestURI.contains(Regex("/api/users/.*./address"))
    },
    authenticationEntryPoint
) {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {

        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        if (authentication === null || !authentication.isAuthenticated) {
            throw InvalidAccessTokenException()
        }

        return authenticationManager.authenticate(authentication)
    }

    class RequireLoginFilterConfigurer : AbstractHttpConfigurer<RequireLoginFilterConfigurer, HttpSecurity>() {
        companion object {
            fun getBean(): RequireLoginFilterConfigurer {
                return RequireLoginFilterConfigurer()
            }
        }

        override fun configure(builder: HttpSecurity?) {
            val filter = RequireLoginFilter(NeoguriAuthenticationEntryPoint())
            filter.authenticationManager = builder!!.getSharedObject(AuthenticationManager::class.java)
            builder.addFilterAfter(filter, AccessTokenAuthenticationFilter::class.java)
        }
    }
}