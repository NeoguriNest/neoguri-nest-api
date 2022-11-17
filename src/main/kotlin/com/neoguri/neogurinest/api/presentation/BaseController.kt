package com.neoguri.neogurinest.api.presentation

import com.neoguri.neogurinest.api.presentation.exception.UnauthorizedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

open class BaseController {
    val BASIC_TOKEN_REGEX = Regex("basic ", RegexOption.IGNORE_CASE)
    val BEARER_TOKEN_REGEX = Regex("bearer ", RegexOption.IGNORE_CASE)

    @Throws(UnauthorizedException::class)
    protected fun getBasicTokenValue(token: String): String {
        return try {
            token.replace(BASIC_TOKEN_REGEX, "")
        } catch (e: Exception) {
            throw UnauthorizedException("Invalid basic auth token format")
        }
    }

    @Throws(UnauthorizedException::class)
    protected fun getBearerTokenValue(token: String): String {
        return try {
            token.replace(BEARER_TOKEN_REGEX, "")
        } catch (e: Exception) {
            throw UnauthorizedException("Invalid bearer auth token format")
        }
    }

    protected fun getAuthentication(): Authentication {
        return SecurityContextHolder.getContext().authentication
    }
}