package com.neoguri.neogurinest.api.presentation

import com.neoguri.neogurinest.api.presentation.exception.BadRequestException
import com.neoguri.neogurinest.api.presentation.exception.UnauthorizedException
import javax.servlet.http.HttpServletRequest

open class BaseController() {
    protected fun getAuthorizationHeader(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")
    }

    @Throws(BadRequestException::class)
    protected fun getBasicHeader(request: HttpServletRequest): String {
        val header = getAuthorizationHeader(request)
        if (header === null) {
            throw UnauthorizedException()
        }
        if (!(header.contains("Basic ") || header.contains("basic "))) {
            throw BadRequestException()
        }

        return header.replace("Basic ", "").replace("basic ", "")
    }

    @Throws(BadRequestException::class)
    protected fun getBearerHeader(request: HttpServletRequest): String {
        val header = getAuthorizationHeader(request)
        if (header === null) {
            throw UnauthorizedException()
        }
        if (!(header.contains("Basic ") || header.contains("basic "))) {
            throw BadRequestException()
        }

        return header.replace("Basic ", "").replace("basic ", "")
    }
}