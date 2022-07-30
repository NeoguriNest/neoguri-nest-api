package com.neoguri.neogurinest.api.domain.user.entity

import java.time.Instant
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user_agreements")
open class UserAgreement {
    @EmbeddedId
    open var id: UserAgreementId? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null
}