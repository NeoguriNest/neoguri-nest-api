package com.neoguri.neogurinest.api.presentation.auth

import com.neoguri.neogurinest.api.application.auth.dto.AuthorizationDto
import com.neoguri.neogurinest.api.application.auth.usecase.LoginUseCaseInterface
import com.neoguri.neogurinest.api.application.user.dto.request.LoginDto
import com.neoguri.neogurinest.api.domain.user.exception.UsernameOrPasswordNotMatchedException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(val login: LoginUseCaseInterface) {

    val logger: Logger = LoggerFactory.getLogger(this@AuthController::class.java)

    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto): ResponseEntity<AuthorizationDto> {
        return try {
            val authorizationDto = login.execute(loginDto)
            ResponseEntity.ok(authorizationDto)
        } catch (e: UsernameOrPasswordNotMatchedException) {
            logger.info(e.message)
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
        }
    }
}