package com.neoguri.neogurinest.api.domain.board.entity

import javax.persistence.*

@Entity
@Table(name = "board_post_hashtags")
open class BoardPostHashtag {
    @EmbeddedId
    open var id: BoardPostHashtagId? = null

    @MapsId("hashTagId")
    @ManyToOne(targetEntity = BoardHashtag::class, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "hash_tag_id", nullable = false)
    open var hashTag: BoardHashtag? = null

    @MapsId("postId")
    @ManyToOne(targetEntity = BoardPost::class, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    open var post: BoardPost? = null

    companion object {
        fun create(post: BoardPost, hashTag: BoardHashtag): BoardPostHashtag {
            val entity = BoardPostHashtag()
            val id = BoardPostHashtagId()
            id.postId = post.id
            id.hashTagId = hashTag.id

            entity.id = id
            entity.post = post
            entity.hashTag = hashTag

            return entity
        }
    }

}