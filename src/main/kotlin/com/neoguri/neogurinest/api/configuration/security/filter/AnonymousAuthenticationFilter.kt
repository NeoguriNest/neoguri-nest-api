package com.neoguri.neogurinest.api.configuration.security.filter

import com.neoguri.neogurinest.api.configuration.security.dto.AnonymousAuthentication
import com.neoguri.neogurinest.api.configuration.security.entrypoint.NeoguriAuthenticationEntryPoint
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.core.Authentication
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class AnonymousAuthenticationFilter(override val authenticationEntryPoint: NeoguriAuthenticationEntryPoint) : AbstractNeoguriAuthenticationFilter(
    RequestMatcher { true },
    authenticationEntryPoint
) {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {

        val authentication = AnonymousAuthentication()

        return authenticationManager.authenticate(authentication)
    }

    class AnonymousAuthenticationFilterConfigurer : AbstractHttpConfigurer<AnonymousAuthenticationFilterConfigurer, HttpSecurity>() {
        companion object {
            fun getBean(): AnonymousAuthenticationFilterConfigurer {
                return AnonymousAuthenticationFilterConfigurer()
            }
        }

        override fun configure(builder: HttpSecurity?) {
            val filter = AnonymousAuthenticationFilter(NeoguriAuthenticationEntryPoint());
            filter.authenticationManager = builder!!.getSharedObject(AuthenticationManager::class.java)
            builder.addFilterAfter(filter, RequireLoginFilter::class.java)
        }
    }

}