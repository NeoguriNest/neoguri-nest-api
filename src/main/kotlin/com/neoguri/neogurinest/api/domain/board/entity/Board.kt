package com.neoguri.neogurinest.api.domain.board.entity

import com.neoguri.neogurinest.api.application.board.dto.request.BoardAddDto
import com.neoguri.neogurinest.api.domain.board.enum.BoardStatus
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "boards")
open class Board {
    @Id
    @Column(name = "board_id", nullable = false, length = 36)
    open var id: UUID? = null

    @Column(name = "nest_id")
    open var nestId: Int? = null

    @Column(name = "title", nullable = false)
    open var title: String? = null

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    open var status: BoardStatus? = null

    @Column(name = "created_at")
    open var createdAt: Instant? = null

    @Column(name = "last_uploaded_at")
    open var lastUploadedAt: Instant? = null

    companion object {
        fun create(boardAddDto: BoardAddDto): Board {
            val board = Board()
            board.id = UUID.randomUUID()
            board.nestId = boardAddDto.nestId
            board.title = boardAddDto.title
            board.status = BoardStatus.CREATED
            board.createdAt = Instant.now()

            return board
        }
    }
}