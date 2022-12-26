package com.neoguri.neogurinest.api.presentation.board

import com.neoguri.neogurinest.api.application.board.dto.request.BoardAddDto
import com.neoguri.neogurinest.api.application.board.dto.request.BoardStatusUpdateDto
import com.neoguri.neogurinest.api.application.board.dto.response.BoardDto
import com.neoguri.neogurinest.api.application.board.usecase.board.BoardAddUseCaseInterface
import com.neoguri.neogurinest.api.application.board.usecase.board.BoardStatusUpdateUseCaseInterface
import com.neoguri.neogurinest.api.domain.board.exception.BoardStatusNotConvertableException
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.domain.common.exception.StatusAlreadyChangedException
import com.neoguri.neogurinest.api.presentation.BaseController
import com.neoguri.neogurinest.api.presentation.exception.BadRequestException
import com.neoguri.neogurinest.api.presentation.exception.ConflictException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/boards")
class BoardController(
    val add: BoardAddUseCaseInterface,
    val updateStatus: BoardStatusUpdateUseCaseInterface
) : BaseController() {

    @PostMapping("")
    fun add(@RequestBody boardAddDto: BoardAddDto): ResponseEntity<BoardDto> {
        if (boardAddDto.title.trim().isEmpty()) {
            throw BadRequestException("Body/title must be has length over 1")
        }

        return try {
            val board = add.execute(boardAddDto)
            ResponseEntity
                .created(URI("/api/boards/${board.boardId}"))
                .body(board)
        } catch (e: DuplicatedEntityException) {
            throw ConflictException(e.message!!)
        }
    }

    @PutMapping("/status")
    fun updateStatus(@RequestBody boardStatusUpdateDto: BoardStatusUpdateDto): ResponseEntity<BoardDto> {

        return try {
            ResponseEntity.ok(updateStatus.execute(boardStatusUpdateDto))
        } catch (e: BoardStatusNotConvertableException) {
            throw BadRequestException(e.message!!)
        } catch (e: StatusAlreadyChangedException) {
            throw ConflictException(e.message!!)
        }
    }
}