package com.neoguri.neogurinest.api.domain.auth.entity

import com.neoguri.neogurinest.api.application.auth.dto.LoginUserDto
import com.neoguri.neogurinest.api.application.auth.service.NeoguriTokenService
import com.neoguri.neogurinest.api.domain.auth.enum.AuthorizationStatus
import com.neoguri.neogurinest.api.util.StringGenerator
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "authorizations")
class Authorization() {
    @Id
    @Column(name = "authentication_id", nullable = false)
    var id: String? = null

    @Column(name = "user_id", nullable = false)
    var userId: Int? = null

    @Column(name = "email", nullable = false)
    var email: String? = null

    @Column(name = "access_token", nullable = false, length = 256)
    var accessToken: String? = null

    @Column(name = "access_token_expired_at", nullable = false)
    var accessTokenExpiredAt: Instant? = null

    @Column(name = "refresh_token", nullable = false, length = 256)
    var refreshToken: String? = null

    @Column(name = "refresh_token_expired_at", nullable = false)
    var refreshTokenExpiredAt: Instant? = null

    @Column(name = "nest_ids", nullable = false)
    var nestIds: String? = null

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    var status: AuthorizationStatus? = null

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant? = null

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant? = null

    constructor(
        id: String,
        userId: Int,
        email: String,
        accessToken: String,
        accessTokenExpiredAt: Instant,
        refreshToken: String,
        refreshTokenExpiredAt: Instant,
        nestIds: String
    ) : this() {
        this.id = id
        this.userId = userId
        this.email = email
        this.accessToken = accessToken
        this.accessTokenExpiredAt = accessTokenExpiredAt
        this.refreshToken = refreshToken
        this.refreshTokenExpiredAt = refreshTokenExpiredAt
        this.nestIds = nestIds
        this.status = AuthorizationStatus.AVAILABLE
        this.createdAt = Instant.now()
        this.updatedAt = Instant.now()
    }

    companion object {
        fun create(
            loginUser: LoginUserDto,
            accessToken: NeoguriTokenService.GeneratedTokenDto,
            refreshToken: NeoguriTokenService.GeneratedTokenDto
        ): Authorization {
            return Authorization(
                StringGenerator.getUuid(false),
                    loginUser.userId,
                    loginUser.email,
                    accessToken.token,
                    accessToken.expiresAt,
                    refreshToken.token,
                    refreshToken.expiresAt,
                    loginUser.nestIds.joinToString(",")
            )
        }
    }

    fun updateAccessToken(accessToken: String, accessTokenExpiredAt: Instant) {
        this.accessToken = accessToken
        this.accessTokenExpiredAt = accessTokenExpiredAt
        this.updatedAt = Instant.now()
    }
}