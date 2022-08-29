package com.neoguri.neogurinest.api.presentation

import com.neoguri.neogurinest.api.presentation.exception.UnauthorizedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import javax.servlet.http.HttpServletRequest

open class BaseController {
    val BASIC_TOKEN_REGEX = Regex("basic ", RegexOption.IGNORE_CASE)
    val BEARER_TOKEN_REGEX = Regex("bearer ", RegexOption.IGNORE_CASE)

    protected fun getAuthorizationHeader(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")
    }

    @Throws(UnauthorizedException::class)
    protected fun getBasicHeader(request: HttpServletRequest): String {
        val header = getAuthorizationHeader(request)
        if (header === null) {
            throw UnauthorizedException("Basic token is not found")
        }
        if (!header.contains(BASIC_TOKEN_REGEX)) {
            throw UnauthorizedException("Invalid format, basic authentication token")
        }

        return header.replace(BASIC_TOKEN_REGEX, "")
    }

    @Throws(UnauthorizedException::class)
    protected fun getBearerHeader(request: HttpServletRequest): String {
        val header = getAuthorizationHeader(request)
        if (header === null) {
            throw UnauthorizedException("Bearer token is not found")
        }
        if (!header.contains(BEARER_TOKEN_REGEX)) {
            throw UnauthorizedException("Invalid format, bearer authentication token")
        }

        return header.replace(BEARER_TOKEN_REGEX, "")
    }

    protected fun getAuthentication(): Authentication {
        return SecurityContextHolder.getContext().authentication
    }
}