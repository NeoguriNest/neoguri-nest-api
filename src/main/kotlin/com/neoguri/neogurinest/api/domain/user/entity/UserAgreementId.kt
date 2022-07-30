package com.neoguri.neogurinest.api.domain.user.entity

import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class UserAgreementId : Serializable {
    @Column(name = "user_id", nullable = false)
    open var userId: Int? = null

    @Column(name = "term_id", nullable = false)
    open var termId: Int? = null

    override fun hashCode(): Int = Objects.hash(userId, termId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as UserAgreementId

        return userId == other.userId &&
                termId == other.termId
    }

    companion object {
        private const val serialVersionUID = -4495243694460430546L
    }
}