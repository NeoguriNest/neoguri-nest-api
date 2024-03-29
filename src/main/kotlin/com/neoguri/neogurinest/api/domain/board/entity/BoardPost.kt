package com.neoguri.neogurinest.api.domain.board.entity

import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostAddDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostUpdateDto
import com.neoguri.neogurinest.api.domain.board.enum.BoardPostStatus
import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.util.StringGenerator
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

    @Column(name = "channel_id", nullable = false, insertable = false, updatable = false)
    open var channelId: String? = null

    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    open var userId: Int? = null

    @Column(name = "title", nullable = false)
    open var title: String? = null

    @Lob
    @Column(name = "content", nullable = false)
    open var content: String? = null

    @Column(name = "status", nullable = false)
    open var status: BoardPostStatus? = null

    @Column(name = "created_at")
    open var createdAt: Instant? = null

    @Column(name = "updated_at")
    open var updatedAt: Instant? = null

    /**
     * relations
     */
    @ManyToOne(targetEntity = User::class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    open var user: User? = null

    @ManyToOne(targetEntity = BoardChannel::class, fetch = FetchType.EAGER)
    @JoinColumn(name = "channel_id", nullable = false)
    open var channel: BoardChannel? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    open var hashTags: MutableList<BoardPostHashtag> = mutableListOf()

    companion object {
        fun create(boardPostAddDto: BoardPostAddDto, channel: BoardChannel, actor: User?): BoardPost {
            val entity = BoardPost()

            entity.id = StringGenerator.getUuid(false)
            entity.channel = channel
            entity.nestId = channel.nestId
            entity.userId = actor?.id
            entity.title = boardPostAddDto.title
            entity.content = boardPostAddDto.content
            entity.title = boardPostAddDto.title
            entity.status = BoardPostStatus.CREATED
            entity.createdAt = Instant.now()

            return entity
        }
    }

    fun update(boardPostUpdateDto: BoardPostUpdateDto, channel: BoardChannel, hashTags: List<BoardPostHashtag>) {
        this.channel = channel
        this.title = boardPostUpdateDto.title
        this.content = boardPostUpdateDto.content
        this.hashTags = hashTags.toMutableList()
        this.updatedAt = Instant.now()
    }

    fun updateStatus(status: BoardPostStatus) {
        this.status = status
        this.updatedAt = Instant.now()
    }
}