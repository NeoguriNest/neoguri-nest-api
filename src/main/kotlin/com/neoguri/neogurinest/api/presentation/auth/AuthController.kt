package com.neoguri.neogurinest.api.presentation.auth

import com.neoguri.neogurinest.api.application.auth.dto.AuthorizationDto
import com.neoguri.neogurinest.api.application.auth.dto.LoginDto
import com.neoguri.neogurinest.api.application.auth.dto.RefreshDto
import com.neoguri.neogurinest.api.application.auth.usecase.LoginUseCaseInterface
import com.neoguri.neogurinest.api.application.auth.usecase.RefreshUseCaseInterface
import com.neoguri.neogurinest.api.domain.auth.entity.Authorization
import com.neoguri.neogurinest.api.domain.auth.exception.RefreshTokenExpiredException
import com.neoguri.neogurinest.api.domain.auth.exception.UsernameOrPasswordNotMatchedException
import com.neoguri.neogurinest.api.presentation.BaseController
import com.neoguri.neogurinest.api.presentation.exception.BadRequestException
import com.neoguri.neogurinest.api.presentation.exception.UnauthorizedException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.Base64Utils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/auth")
class AuthController(
    val login: LoginUseCaseInterface,
    val refresh: RefreshUseCaseInterface
): BaseController() {

    val logger: Logger = LoggerFactory.getLogger(this@AuthController::class.java)

    @PostMapping("/login")
    fun login(request: HttpServletRequest): ResponseEntity<AuthorizationDto> {
        val token: String = getBasicHeader(request)

        val credentials = String(Base64Utils.decodeFromUrlSafeString(token)).split(":")
        if (credentials.size != 2) {
            throw BadRequestException()
        }

        return try {
            val authorizationDto = login.execute(LoginDto(credentials[0], credentials[1]))
            ResponseEntity.ok(authorizationDto)
        } catch (e: UsernameOrPasswordNotMatchedException) {
            logger.info(e.message)
            throw UnauthorizedException()
        }
    }

    @PostMapping("/refresh")
    fun refresh(request: HttpServletRequest): ResponseEntity<AuthorizationDto> {
        val refreshToken: String = getBearerHeader(request)

        return try {
            val authorizationDto = refresh.execute(RefreshDto(refreshToken))
            ResponseEntity.ok(authorizationDto)
        } catch (e: RefreshTokenExpiredException) {
            logger.info(e.message)
            throw UnauthorizedException()
        }
    }
}