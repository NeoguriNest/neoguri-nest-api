package com.neoguri.neogurinest.api.domain.board.entity

import com.neoguri.neogurinest.api.application.board.action.post.dto.BoardPostReportAddDto
import com.neoguri.neogurinest.api.domain.board.enum.BoardPostReportStatus
import com.neoguri.neogurinest.api.domain.board.enum.BoardPostReportType
import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.util.StringGenerator
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.time.Instant
import javax.persistence.*

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "board_post_reports")
open class BoardPostReport {
    @Id
    @Column(name = "report_id", nullable = false, length = 36)
    open var id: String? = null

    @Column(name = "type", nullable = false)
    open var type: BoardPostReportType? = null

    @Column(name = "user_id", insertable = false, updatable = false)
    open var userId: Int? = null

    @Column(name = "post_id", insertable = false, updatable = false)
    open var postId: String? = null

    @Lob
    @Column(name = "content", nullable = false)
    open var content: String? = null

    @Lob
    @Column(name = "reported_content", nullable = false)
    open var reportedContent: String? = null

    @Column(name = "status", nullable = false)
    open var status: BoardPostReportStatus? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null

    @Column(name = "updated_at")
    open var updatedAt: Instant? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    open var user: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    open var post: BoardPost? = null

    companion object {
        fun create(addDto: BoardPostReportAddDto, post: BoardPost, actor: User): BoardPostReport {
            val entity = BoardPostReport()
            entity.id = StringGenerator.getUuid(false)
            entity.type = addDto.type
            entity.post = post
            entity.user = actor
            entity.content = addDto.content
            entity.reportedContent = post.content
            entity.createdAt = Instant.now()
            entity.status = BoardPostReportStatus.CREATED

            return entity
        }
    }
}