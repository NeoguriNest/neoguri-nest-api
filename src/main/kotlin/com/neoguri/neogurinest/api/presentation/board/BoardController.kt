package com.neoguri.neogurinest.api.presentation.board

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardAddDto
import com.neoguri.neogurinest.api.application.board.channel.dto.BoardStatusUpdateDto
import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelDto
import com.neoguri.neogurinest.api.application.board.channel.usecase.BoardAddUseCaseInterface
import com.neoguri.neogurinest.api.application.board.channel.usecase.BoardStatusUpdateUseCaseInterface
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelStatusNotConvertableException
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.domain.common.exception.StatusAlreadyChangedException
import com.neoguri.neogurinest.api.presentation.BaseController
import com.neoguri.neogurinest.api.presentation.exception.BadRequestException
import com.neoguri.neogurinest.api.presentation.exception.ConflictException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/board/channels")
class BoardController(
    val add: BoardAddUseCaseInterface,
    val updateStatus: BoardStatusUpdateUseCaseInterface
) : BaseController() {

    @PostMapping("")
    fun add(@RequestBody boardAddDto: BoardAddDto): ResponseEntity<BoardChannelDto> {
        if (boardAddDto.title.trim().isEmpty()) {
            throw BadRequestException("Body/title must be has length over 1")
        }

        return try {
            val board = add.execute(boardAddDto)
            ResponseEntity
                .created(URI("/api/board/channels/${board.channelId}"))
                .body(board)
        } catch (e: DuplicatedEntityException) {
            throw ConflictException(e.message!!)
        }
    }

    @PutMapping("/{boardId}/status")
    fun updateStatus(@RequestBody boardStatusUpdateDto: BoardStatusUpdateDto): ResponseEntity<BoardChannelDto> {

        return try {
            ResponseEntity.ok(updateStatus.execute(boardStatusUpdateDto))
        } catch (e: BoardChannelStatusNotConvertableException) {
            throw BadRequestException(e.message!!)
        } catch (e: StatusAlreadyChangedException) {
            throw ConflictException(e.message!!)
        }
    }
}