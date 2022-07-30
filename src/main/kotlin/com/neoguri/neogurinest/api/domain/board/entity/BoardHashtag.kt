package com.neoguri.neogurinest.api.domain.board.entity

import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "board_hashtags")
open class BoardHashtag {
    @Id
    @Column(name = "hash_tag_id", nullable = false, length = 36)
    open var id: String? = null

    @Column(name = "name", nullable = false)
    open var name: String? = null

    @Column(name = "total_post_count", nullable = false)
    open var totalPostCount: Int? = null

    @Column(name = "last_uploaded_at", nullable = false)
    open var lastUploadedAt: Instant? = null
}