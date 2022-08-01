package com.neoguri.neogurinest.api.domain.board.entity

import com.neoguri.neogurinest.api.domain.board.enum.BoardStatus
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "boards")
open class Board {
    @Id
    @Column(name = "board_id", nullable = false, length = 36)
    open var id: String? = null

    @Column(name = "nest_id", nullable = false)
    open var nestId: Int? = null

    @Column(name = "title", nullable = false)
    open var title: String? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null

    @Column(name = "last_uploaded_at", nullable = false)
    open var lastUploadedAt: Instant? = null

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    open var status: BoardStatus? = null
}