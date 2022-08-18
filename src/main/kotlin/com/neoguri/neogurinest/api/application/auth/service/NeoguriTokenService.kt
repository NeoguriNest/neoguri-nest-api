package com.neoguri.neogurinest.api.application.auth.service

import com.neoguri.neogurinest.api.application.auth.dto.LoginUserDto
import com.neoguri.neogurinest.api.util.StringGenerator
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class NeoguriTokenService {

    private var SECRET_KEY: String? = null

    private var ACCESS_TOKEN_DURATION: Long? = null

    private var REFRESH_TOKEN_DURATION: Long? = null

    private val DURATION_UNIT = ChronoUnit.MINUTES

    private val REFRESH_TOKEN_LENGTH = 128

    fun generateAccessToken(payload: LoginUserDto): GeneratedTokenDto {
        val now = Instant.now()
        val expiresAt = Instant.from(now).plus(ACCESS_TOKEN_DURATION!!, DURATION_UNIT)

        val accessToken = Jwts.builder()
            .setSubject(payload.loginId)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(expiresAt))
            .signWith(Keys.hmacShaKeyFor(SECRET_KEY!!.toByteArray(StandardCharsets.UTF_8)))
            .compact()

        return GeneratedTokenDto(
            accessToken,
            expiresAt,
            ACCESS_TOKEN_DURATION!!
        )
    }

    fun generateRefreshToken(): GeneratedTokenDto {
        val now = Instant.now()
        val expiresAt = Instant.from(now).plus(REFRESH_TOKEN_DURATION!!, DURATION_UNIT)

        return GeneratedTokenDto(
            StringGenerator.random(REFRESH_TOKEN_LENGTH),
            expiresAt,
            REFRESH_TOKEN_DURATION!!
        )
    }

    data class GeneratedTokenDto(
        val token: String,
        val expiresAt: Instant,
        val duration: Long
    )

    @Value("\${neoguri.jwt.secret}")
    fun setSecretKey(secretKey: String) {
        this.SECRET_KEY = secretKey
    }

    @Value("\${neoguri.jwt.duration.access-token}")
    fun setAccessTokenDuration(accessTokenDuration: Long) {
        this.ACCESS_TOKEN_DURATION = accessTokenDuration
    }

    @Value("\${neoguri.jwt.duration.refresh-token}")
    fun setRefreshTokenDuration(refreshTokenDuration: Long) {
        this.REFRESH_TOKEN_DURATION = refreshTokenDuration
    }

}
