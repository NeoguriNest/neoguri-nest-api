package com.neoguri.neogurinest.api.domain.board.entity

import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class BoardPostLikeId : Serializable {
    @Column(name = "post_id", nullable = false, length = 36)
    open var postId: String? = null

    @Column(name = "user_id", nullable = false)
    open var userId: Int? = null

    override fun hashCode(): Int = Objects.hash(postId, userId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as BoardPostLikeId

        return postId == other.postId &&
                userId == other.userId
    }

    companion object {
        private const val serialVersionUID = -4792866806692287161L
    }
}