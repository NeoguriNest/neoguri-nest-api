package com.neoguri.neogurinest.api.domain.user.entity

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "users")
open class User {
    @Id
    @Column(name = "user_id", nullable = false)
    open var id: Int? = null

    @Column(name = "login_id", nullable = false)
    open var loginId: String? = null

    @Column(name = "password", nullable = false)
    open var password: String? = null

    @Column(name = "email", nullable = false)
    open var email: String? = null

    @Column(name = "nickname", nullable = false)
    open var nickname: String? = null

    @Column(name = "birthdate", nullable = false)
    open var birthdate: String? = null

    @Column(name = "introduction_text", nullable = false)
    open var introductionText: String? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null

    @Column(name = "updated_at", nullable = false)
    open var updatedAt: Instant? = null

    @Lob
    @Column(name = "gender", nullable = false)
    open var gender: String? = null

    @Lob
    @Column(name = "status", nullable = false)
    open var status: String? = null
}