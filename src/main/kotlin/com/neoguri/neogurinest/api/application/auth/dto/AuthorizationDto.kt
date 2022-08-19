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
    val loginId: String,
    val status: DescribedEnumDto<AuthorizationStatus>,
    val tokenType: String = "Bearer"
) {
    companion object {
        fun fromEntity(entity: Authorization): AuthorizationDto {
            return AuthorizationDto(
                entity.id!!,
                entity.accessToken!!,
                entity.accessTokenExpiredAt!!,
                entity.refreshToken!!,
                entity.refreshTokenExpiredAt!!,
                entity.userId!!,
                entity.loginId!!,
                DescribedEnumDto(
                    entity.status!!,
                    when (entity.status) {
                        AuthorizationStatus.AVAILABLE -> "활성화됨"
                        else -> "만료됨"
                    }
                )
            )
        }
    }
}
