package com.neoguri.neogurinest.api.domain.user.entity

import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class UserFileId : Serializable {
    @Column(name = "user_id", nullable = false)
    open var userId: Int? = null

    @Column(name = "file_id", nullable = false)
    open var fileId: String? = null

    override fun hashCode(): Int = Objects.hash(userId, fileId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as UserFileId

        return userId == other.userId &&
                fileId == other.fileId
    }

    companion object {
        private const val serialVersionUID = 7526480468104126668L
    }
}