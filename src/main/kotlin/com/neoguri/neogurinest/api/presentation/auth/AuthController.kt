package com.neoguri.neogurinest.api.presentation.auth

import com.neoguri.neogurinest.api.application.auth.dto.AuthorizationDto
import com.neoguri.neogurinest.api.application.auth.dto.LoginDto
import com.neoguri.neogurinest.api.application.auth.dto.RefreshDto
import com.neoguri.neogurinest.api.application.auth.usecase.LoginUseCase
import com.neoguri.neogurinest.api.application.auth.usecase.RefreshUseCase
import com.neoguri.neogurinest.api.domain.auth.exception.RefreshTokenExpiredException
import com.neoguri.neogurinest.api.domain.auth.exception.UsernameOrPasswordNotMatchedException
import com.neoguri.neogurinest.api.presentation.BaseController
import com.neoguri.neogurinest.api.presentation.exception.UnauthorizedException
import org.springframework.http.ResponseEntity
import org.springframework.util.Base64Utils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/auth")
class AuthController(
    val login: LoginUseCase,
    val refresh: RefreshUseCase
): BaseController() {

    /**
     * @uri POST /api/auth/sign-in
     * 로그인
     */
    @PostMapping("/sign-in")
    fun login(
        @RequestHeader(name = "Authorization", required = true) basicToken: String,
        request: HttpServletRequest
    ): ResponseEntity<AuthorizationDto> {

        val token: String = getBasicTokenValue(basicToken)
        val credentials = String(Base64Utils.decodeFromUrlSafeString(token)).split(":")
        if (credentials.size != 2) {
            throw UnauthorizedException("Invalid format, basic authentication token")
        }

        return try {
            val authorizationDto = login.execute(LoginDto(credentials[0], credentials[1]))
            ResponseEntity.ok(authorizationDto)
        } catch (e: UsernameOrPasswordNotMatchedException) {
            throw UnauthorizedException(e.message!!)
        }
    }

    /**
     * @uri POST /api/auth/refresh
     * 토큰 갱신
     */
    @PostMapping("/refresh")
    fun refresh(
        @RequestHeader(name = "Authorization", required = true) bearerToken: String,
        request: HttpServletRequest): ResponseEntity<AuthorizationDto> {
        val refreshToken: String = getBearerTokenValue(bearerToken)

        return try {
            val authorizationDto = refresh.execute(RefreshDto(refreshToken))
            ResponseEntity.ok(authorizationDto)
        } catch (e: RefreshTokenExpiredException) {
            throw UnauthorizedException(e.message!!)
        }
    }
}