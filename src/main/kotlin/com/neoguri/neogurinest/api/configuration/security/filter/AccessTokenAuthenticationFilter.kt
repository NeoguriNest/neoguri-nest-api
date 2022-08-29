package com.neoguri.neogurinest.api.configuration.security.filter

import com.neoguri.neogurinest.api.configuration.security.dto.AccessTokenAuthentication
import com.neoguri.neogurinest.api.configuration.security.entrypoint.NeoguriAuthenticationEntryPoint
import com.neoguri.neogurinest.api.domain.auth.exception.InvalidAccessTokenException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.core.Authentication
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AccessTokenAuthenticationFilter(override val authenticationEntryPoint: AuthenticationEntryPoint) : AbstractNeoguriAuthenticationFilter(
    RequestMatcher { true },
    authenticationEntryPoint
) {
    val BEARER_PREFIX: Regex = Regex("bearer ", RegexOption.IGNORE_CASE)

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication? {
        if (request === null) {
            throw RuntimeException("Request must not be null")
        }

        val authorization: String? = request.getHeader("Authorization")
        if (authorization === null) {
            return null
        }

        if (!(authorization.contains(BEARER_PREFIX))) {
            throw InvalidAccessTokenException()
        }

        val accessToken: String = authorization.replace(BEARER_PREFIX, "")
        val authentication = AccessTokenAuthentication(accessToken)

        return authenticationManager.authenticate(authentication)
    }

    class AccessTokenAuthenticationFilterConfigurer : AbstractHttpConfigurer<AccessTokenAuthenticationFilterConfigurer, HttpSecurity>() {
        companion object {
            fun getBean(): AccessTokenAuthenticationFilterConfigurer {
                return AccessTokenAuthenticationFilterConfigurer()
            }
        }

        override fun configure(builder: HttpSecurity?) {
            val filter = AccessTokenAuthenticationFilter(NeoguriAuthenticationEntryPoint())

            filter.authenticationManager = builder!!.getSharedObject(AuthenticationManager::class.java)
            builder.addFilterBefore(filter, CsrfFilter::class.java)
        }
    }

}