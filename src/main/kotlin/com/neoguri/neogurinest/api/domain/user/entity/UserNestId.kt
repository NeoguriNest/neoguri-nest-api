package com.neoguri.neogurinest.api.domain.user.entity

import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class UserNestId : Serializable {
    @Column(name = "user_id", nullable = false)
    open var userId: Int? = null

    @Column(name = "nest_id", nullable = false)
    open var nestId: Int? = null

    override fun hashCode(): Int = Objects.hash(userId, nestId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as UserNestId

        return userId == other.userId &&
                nestId == other.nestId
    }

    companion object {
        private const val serialVersionUID = -2302719886587941756L
    }
}