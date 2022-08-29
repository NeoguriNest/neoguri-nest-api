package com.neoguri.neogurinest.api.configuration.security.filter

import com.neoguri.neogurinest.api.configuration.security.dto.AccessTokenAuthentication
import com.neoguri.neogurinest.api.configuration.security.entrypoint.NeoguriAuthenticationEntryPoint
import com.neoguri.neogurinest.api.presentation.exception.BadRequestException
import com.neoguri.neogurinest.api.presentation.exception.UnauthorizedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class AbstractNeoguriAuthenticationFilter(requestMatcher: RequestMatcher, open val authenticationEntryPoint: AuthenticationEntryPoint)
: AbstractAuthenticationProcessingFilter(requestMatcher) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        this.doFilter(request as HttpServletRequest?, response as HttpServletResponse?, chain)
    }

    @Throws(IOException::class, ServletException::class)
    protected open fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {
        if (!requiresAuthentication(request, response)) {
            chain!!.doFilter(request, response)
            return
        }
        try {
            val authenticationResult = attemptAuthentication(request, response)
            if (authenticationResult !== null && authenticationResult.isAuthenticated) {
                SecurityContextHolder.getContext().authentication = authenticationResult
            }

            chain!!.doFilter(request, response)

            successfulAuthentication(request, response, chain, authenticationResult)
        } catch (failed: InternalAuthenticationServiceException) {
            logger.error("An internal error occurred while trying to authenticate the user.", failed)
            unsuccessfulAuthentication(request, response, failed)
        } catch (ex: AuthenticationException) {
            // Authentication failed
            authenticationEntryPoint.commence(request, response, ex)
        }
    }

}