package com.neoguri.neogurinest.api.configuration.security.filter

import com.neoguri.neogurinest.api.configuration.security.dto.AnonymousAuthentication
import com.neoguri.neogurinest.api.configuration.security.entrypoint.NeoguriAuthenticationEntryPoint
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.util.matcher.RequestMatcher
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class AnonymousAuthenticationFilter(override val authenticationEntryPoint: NeoguriAuthenticationEntryPoint) : AbstractNeoguriAuthenticationFilter(
    RequestMatcher { true },
    authenticationEntryPoint
) {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {
        if (!requiresAuthentication(request, response)) {
            chain!!.doFilter(request, response)
            return
        }
        try {
            val authentication: Authentication? = SecurityContextHolder.getContext().authentication
            if (authentication !== null && authentication.isAuthenticated) {
                chain!!.doFilter(request, response)
                return
            }

            val authenticationResult = attemptAuthentication(request, response)
            if (authenticationResult.isAuthenticated) {
                SecurityContextHolder.getContext().authentication = authenticationResult
            }

            chain!!.doFilter(request, response)

//            successfulAuthentication(request, response, chain, authenticationResult)
        } catch (failed: InternalAuthenticationServiceException) {
            logger.error("An internal error occurred while trying to authenticate the user.", failed)
            unsuccessfulAuthentication(request, response, failed)
        } catch (ex: AuthenticationException) {
            // Authentication failed
            authenticationEntryPoint.commence(request, response, ex)
        }
    }

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