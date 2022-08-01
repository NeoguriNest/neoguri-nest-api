package com.neoguri.neogurinest.api.domain.auth.entity

import com.neoguri.neogurinest.api.domain.auth.enum.AuthorizationStatus
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "authorizations")
open class Authorization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authentication_id", nullable = false)
    open var id: Int? = null

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

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    open var status: AuthorizationStatus? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null

    @Column(name = "updated_at", nullable = false)
    open var updatedAt: Instant? = null
}