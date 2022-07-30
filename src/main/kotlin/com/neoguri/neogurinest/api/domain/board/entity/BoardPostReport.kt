package com.neoguri.neogurinest.api.domain.board.entity

import com.neoguri.neogurinest.api.domain.user.entity.User
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "board_post_reports")
open class BoardPostReport {
    @Id
    @Column(name = "report_id", nullable = false, length = 36)
    open var id: String? = null

    @Column(name = "comment_id", nullable = false)
    open var commentId: String? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    open var user: User? = null

    @Column(name = "type", nullable = false)
    open var type: Int? = null

    @Lob
    @Column(name = "content", nullable = false)
    open var content: String? = null

    @Lob
    @Column(name = "reported_content", nullable = false)
    open var reportedContent: String? = null

    @Column(name = "status", nullable = false)
    open var status: Int? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null

    @Column(name = "updated_at", nullable = false)
    open var updatedAt: Instant? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    open var post: BoardPost? = null
}