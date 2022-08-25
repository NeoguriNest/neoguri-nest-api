package com.neoguri.neogurinest.api.presentation

import com.neoguri.neogurinest.api.presentation.exception.BadRequestException
import com.neoguri.neogurinest.api.presentation.exception.UnauthorizedException
import javax.servlet.http.HttpServletRequest

open class BaseController() {
    val BASIC_TOKEN_REGEX = Regex("basic ", RegexOption.IGNORE_CASE)
    val BEARER_TOKEN_REGEX = Regex("bearer ", RegexOption.IGNORE_CASE)

    protected fun getAuthorizationHeader(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")
    }

    @Throws(
        BadRequestException::class,
        UnauthorizedException::class
    )
    protected fun getBasicHeader(request: HttpServletRequest): String {
        val header = getAuthorizationHeader(request)
        if (header === null) {
            throw UnauthorizedException()
        }
        if (!header.contains(BASIC_TOKEN_REGEX)) {
            throw BadRequestException()
        }

        return header.replace(BASIC_TOKEN_REGEX, "")
    }

    @Throws(
        BadRequestException::class,
        UnauthorizedException::class
    )
    protected fun getBearerHeader(request: HttpServletRequest): String {
        val header = getAuthorizationHeader(request)
        if (header === null) {
            throw UnauthorizedException()
        }
        if (!header.contains(BEARER_TOKEN_REGEX)) {
            throw BadRequestException()
        }

        return header.replace(BEARER_TOKEN_REGEX, "")
    }
}