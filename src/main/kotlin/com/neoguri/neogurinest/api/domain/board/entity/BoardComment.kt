package com.neoguri.neogurinest.api.domain.board.entity

import com.neoguri.neogurinest.api.domain.board.enum.BoardCommentStatus
import com.neoguri.neogurinest.api.domain.user.entity.User
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "board_comments")
open class BoardComment {
    @Id
    @Column(name = "comment_id", nullable = false, length = 36)
    open var id: String? = null

    @Column(name = "nest_id")
    open var nestId: Int? = null

    @Column(name = "board_id", nullable = false)
    open var boardId: String? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    open var post: BoardPost? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    open var user: User? = null

    @Lob
    @Column(name = "content", nullable = false)
    open var content: String? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent_id")
    open var parent: BoardComment? = null

    @Column(name = "status", nullable = false)
    open var status: BoardCommentStatus? = null

    @Column(name = "created_at")
    open var createdAt: Instant? = null

    @Column(name = "updated_at")
    open var updatedAt: Instant? = null

}