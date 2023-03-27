package com.neoguri.neogurinest.api.domain.board.entity

import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentAddDto
import com.neoguri.neogurinest.api.domain.board.enum.BoardCommentStatus
import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.util.StringGenerator
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

    @Column(name = "channel_id", nullable = false)
    open var channelId: String? = null

    @Column(name = "post_id", nullable = false, insertable = false, updatable = false)
    open var postId: String? = null

    @Lob
    @Column(name = "content", nullable = false)
    open var content: String? = null

    @Column(name = "status", nullable = false)
    open var status: BoardCommentStatus? = null

    @Column(name = "created_at")
    open var createdAt: Instant? = null

    @Column(name = "updated_at")
    open var updatedAt: Instant? = null

    /**
     * relations
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    open var post: BoardPost? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    open var user: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    open var parent: BoardComment? = null

    fun updateContent(newContent: String) {
        this.content = newContent
        this.updatedAt = Instant.now()
    }

    companion object {
        fun create(actor: User, addDto: BoardCommentAddDto, post: BoardPost, parent: BoardComment?): BoardComment {
            val comment = BoardComment()
            comment.id = StringGenerator.getUuid(false)
            comment.nestId = post.nestId
            comment.post = post
            comment.channelId = post.channelId
            comment.user = actor
            comment.content = addDto.content
            comment.parent = parent
            comment.status = BoardCommentStatus.CREATED
            comment.createdAt = Instant.now()

            return comment
        }
    }
}