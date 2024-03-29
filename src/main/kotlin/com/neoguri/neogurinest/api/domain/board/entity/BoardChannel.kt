package com.neoguri.neogurinest.api.domain.board.entity

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardAddDto
import com.neoguri.neogurinest.api.domain.board.enum.BoardStatus
import com.neoguri.neogurinest.api.util.StringGenerator
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "board_channels")
open class BoardChannel {
    @Id
    @Column(name = "channel_id", nullable = false, length = 36)
    open var id: String? = null

    @Column(name = "nest_id")
    open var nestId: Int? = null

    @Column(name = "title", nullable = false)
    open var title: String? = null

    @Column(name = "status", nullable = false)
    open var status: BoardStatus? = null

    @Column(name = "created_at")
    open var createdAt: Instant? = null

    @Column(name = "last_uploaded_at")
    open var lastUploadedAt: Instant? = null

    companion object {
        fun create(boardAddDto: BoardAddDto): BoardChannel {
            val board = BoardChannel()
            board.id = StringGenerator.getUuid(false)
            board.nestId = boardAddDto.nestId
            board.title = boardAddDto.title
            board.status = BoardStatus.CREATED
            board.createdAt = Instant.now()

            return board
        }
    }

    fun isPostAddable(): Boolean {
        return status === BoardStatus.ACTIVATED
    }

    fun isStatusConvertable(to: BoardStatus): Boolean {
        return (BoardStatus.getConvertable(to).any{ status === it })
    }

}