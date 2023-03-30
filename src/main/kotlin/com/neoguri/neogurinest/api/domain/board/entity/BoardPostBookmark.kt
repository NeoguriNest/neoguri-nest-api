package com.neoguri.neogurinest.api.domain.board.entity

import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
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

    companion object {

        fun create(post: BoardPost, actor: BoardActor): BoardPostBookmark {
            val entity = BoardPostBookmark()
            entity.id = BoardPostBookmarkId(post.id, actor.user.id)
            entity.createdAt = Instant.now()

            return entity
        }

    }

}