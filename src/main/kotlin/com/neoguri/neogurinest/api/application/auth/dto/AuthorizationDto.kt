package com.neoguri.neogurinest.api.application.auth.dto

import com.neoguri.neogurinest.api.application.common.dto.DescribedEnumDto
import com.neoguri.neogurinest.api.domain.auth.entity.Authorization
import com.neoguri.neogurinest.api.domain.auth.enum.AuthorizationStatus
import java.time.Instant

data class AuthorizationDto(
    val authenticationId: String,
    val accessToken: String,
    val accessTokenExpiredAt: Instant,
    val refreshToken: String,
    val refreshTokenExpiredAt: Instant,
    val userId: Int,
    val email: String,
    val status: DescribedEnumDto<AuthorizationStatus>,
    val tokenType: String = "Bearer"
) {
    companion object {
        fun of(entity: Authorization): AuthorizationDto {
            return AuthorizationDto(
                entity.id!!,
                entity.accessToken!!,
                entity.accessTokenExpiredAt!!,
                entity.refreshToken!!,
                entity.refreshTokenExpiredAt!!,
                entity.userId!!,
                entity.email!!,
                DescribedEnumDto(
                    entity.status!!,
                    when (entity.status) {
                        AuthorizationStatus.AVAILABLE -> "활성화됨"
                        AuthorizationStatus.EXPIRED -> "만료됨"
                        else -> "-"
                    }
                )
            )
        }
    }
}
