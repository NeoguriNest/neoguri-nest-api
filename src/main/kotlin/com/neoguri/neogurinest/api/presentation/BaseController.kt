package com.neoguri.neogurinest.api.presentation

import com.neoguri.neogurinest.api.application.auth.dto.LoginUserDto
import com.neoguri.neogurinest.api.presentation.exception.ForbiddenException
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

    protected fun getLoginUserDto(): LoginUserDto? {
        val authentication: Authentication = getAuthentication()
        if (authentication.details !== null
            && LoginUserDto::class.java.isAssignableFrom(authentication.details::class.java)) {
            return authentication.details as LoginUserDto
        }

        return null
    }

    protected fun isResourceOwnerOrFail(ownerId: Int): Boolean {
        val loginUser: LoginUserDto? = getLoginUserDto()
        if (loginUser === null || loginUser.userId != ownerId) {
            throw ForbiddenException("There's no permission to this action")
        }

        return true
    }
}