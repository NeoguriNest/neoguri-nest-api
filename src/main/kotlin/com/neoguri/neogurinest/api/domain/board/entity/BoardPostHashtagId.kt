package com.neoguri.neogurinest.api.domain.board.entity

import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class BoardPostHashtagId : Serializable {
    @Column(name = "hash_tag_id", nullable = false, length = 36)
    open var hashTagId: String? = null

    @Column(name = "post_id", nullable = false, length = 36)
    open var postId: String? = null

    override fun hashCode(): Int = Objects.hash(hashTagId, postId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as BoardPostHashtagId

        return hashTagId == other.hashTagId &&
                postId == other.postId
    }

    companion object {
        private const val serialVersionUID = 6572543863317317110L
    }
}