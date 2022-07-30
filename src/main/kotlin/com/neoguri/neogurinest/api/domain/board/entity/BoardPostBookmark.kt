package com.neoguri.neogurinest.api.domain.board.entity

import java.time.Instant
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "board_post_bookmarks")
open class BoardPostBookmark {
    @EmbeddedId
    open var id: BoardPostBookmarkId? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null
}