package com.neoguri.neogurinest.api.domain.board.entity

import javax.persistence.*

@Entity
@Table(name = "board_post_hashtags")
open class BoardPostHashtag {
    @EmbeddedId
    open var id: BoardPostHashtagId? = null

    @MapsId("hashTagId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hash_tag_id", nullable = false)
    open var hashTag: BoardHashtag? = null

    @MapsId("postId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    open var post: BoardPost? = null
}