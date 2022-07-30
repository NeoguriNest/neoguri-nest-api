package com.neoguri.neogurinest.api.domain.board.entity

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "board_posts")
open class BoardPost {
    @Id
    @Column(name = "post_id", nullable = false, length = 36)
    open var id: String? = null

    @Column(name = "nest_id", nullable = false)
    open var nestId: Int? = null

    @Column(name = "board_id", nullable = false)
    open var boardId: String? = null

    @Column(name = "user_id", nullable = false)
    open var userId: Int? = null

    @Column(name = "title", nullable = false)
    open var title: String? = null

    @Lob
    @Column(name = "content", nullable = false)
    open var content: String? = null

    @Column(name = "status", nullable = false)
    open var status: Int? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null

    @Column(name = "updated_at", nullable = false)
    open var updatedAt: Instant? = null
}