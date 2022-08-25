package com.neoguri.neogurinest.api.domain.auth.entity

import com.neoguri.neogurinest.api.application.auth.service.NeoguriTokenService
import com.neoguri.neogurinest.api.application.auth.dto.LoginUserDto
import com.neoguri.neogurinest.api.domain.auth.enum.AuthorizationStatus
import com.neoguri.neogurinest.api.util.StringGenerator
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "authorizations")
open class Authorization() {
    @Id
    @Column(name = "authentication_id", nullable = false)
    open var id: String? = null

    @Column(name = "user_id", nullable = false)
    open var userId: Int? = null

    @Column(name = "login_id", nullable = false)
    open var loginId: String? = null

    @Column(name = "access_token", nullable = false, length = 256)
    open var accessToken: String? = null

    @Column(name = "access_token_expired_at", nullable = false)
    open var accessTokenExpiredAt: Instant? = null

    @Column(name = "refresh_token", nullable = false, length = 256)
    open var refreshToken: String? = null

    @Column(name = "refresh_token_expired_at", nullable = false)
    open var refreshTokenExpiredAt: Instant? = null

    @Column(name = "nest_ids", nullable = false)
    open var nestIds: String? = null

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    open var status: AuthorizationStatus? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null

    @Column(name = "updated_at", nullable = false)
    open var updatedAt: Instant? = null

    constructor(
        id: String,
        userId: Int,
        loginId: String,
        accessToken: String,
        accessTokenExpiredAt: Instant,
        refreshToken: String,
        refreshTokenExpiredAt: Instant,
        nestIds: String
    ) : this() {
        this.id = id
        this.userId = userId
        this.loginId = loginId
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
                    loginUser.loginId,
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