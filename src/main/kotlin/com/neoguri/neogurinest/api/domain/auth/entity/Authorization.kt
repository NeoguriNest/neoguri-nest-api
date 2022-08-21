package com.neoguri.neogurinest.api.domain.auth.entity

import com.neoguri.neogurinest.api.application.auth.service.NeoguriTokenService
import com.neoguri.neogurinest.api.application.auth.dto.LoginUserDto
import com.neoguri.neogurinest.api.domain.auth.enum.AuthorizationStatus
import com.neoguri.neogurinest.api.util.StringGenerator
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "authorizations")
open class Authorization {
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

    companion object {
        fun create(
            loginUser: LoginUserDto,
            accessToken: NeoguriTokenService.GeneratedTokenDto,
            refreshToken: NeoguriTokenService.GeneratedTokenDto
        ): Authorization {
            val self = Authorization()
            self.id = StringGenerator.getUuid(false)
            self.userId = loginUser.userId
            self.loginId = loginUser.loginId
            self.accessToken = accessToken.token
            self.accessTokenExpiredAt = accessToken.expiresAt
            self.refreshToken = refreshToken.token
            self.refreshTokenExpiredAt = refreshToken.expiresAt
            self.status = AuthorizationStatus.AVAILABLE
            self.nestIds = loginUser.nestIds.joinToString(",")
            self.createdAt = Instant.now()
            self.updatedAt = Instant.now()

            return self
        }
    }
}