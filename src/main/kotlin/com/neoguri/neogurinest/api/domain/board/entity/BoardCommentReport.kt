package com.neoguri.neogurinest.api.domain.board.entity

import com.neoguri.neogurinest.api.domain.board.enum.BoardCommentReportStatus
import com.neoguri.neogurinest.api.domain.board.enum.BoardCommentReportType
import com.neoguri.neogurinest.api.domain.user.entity.User
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "board_comment_reports")
open class BoardCommentReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id", nullable = false, length = 36)
    open var id: String? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_id", nullable = false)
    open var comment: BoardComment? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    open var user: User? = null

    @Lob
    @Column(name = "content", nullable = false)
    open var content: String? = null

    @Lob
    @Column(name = "reported_content", nullable = false)
    open var reportedContent: String? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null

    @Column(name = "updated_at", nullable = false)
    open var updatedAt: Instant? = null

    @Column(name = "type", nullable = false)
    open var type: BoardCommentReportType? = null

    @Column(name = "status", nullable = false)
    open var status: BoardCommentReportStatus? = null
}