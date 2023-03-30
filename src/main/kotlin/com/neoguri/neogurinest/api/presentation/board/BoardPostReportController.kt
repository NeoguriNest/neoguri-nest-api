package com.neoguri.neogurinest.api.presentation.board

import com.neoguri.neogurinest.api.application.aspect.BoardContext
import com.neoguri.neogurinest.api.application.board.action.post.dto.BoardPostReportAddDto
import com.neoguri.neogurinest.api.application.board.action.post.dto.BoardPostReportDto
import com.neoguri.neogurinest.api.application.board.action.post.usecase.BoardPostReportAddUseCase
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotAvailableStatusException
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotFoundException
import com.neoguri.neogurinest.api.presentation.BaseController
import com.neoguri.neogurinest.api.presentation.exception.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/board/post-reports")
class BoardPostReportController(
    val add: BoardPostReportAddUseCase
) : BaseController() {

    @PostMapping
    fun add(
        @RequestBody addDto: BoardPostReportAddDto,
    ): ResponseEntity<BoardPostReportDto> {
        val boardContext = BoardContext.getInstance()
        if (boardContext.isAnonymous()) {
            throw UnauthorizedException("You must be authorized for this api")
        }

        return try {
            val payload = add.execute(addDto, boardContext.actor!!)
            ResponseEntity
                .created(URI("/api/board/post-reports/${payload.reportId}"))
                .body(payload)
        } catch (e: BoardChannelNotFoundException) {
            throw NotFoundException(e.message!!)
        } catch (e: BoardChannelNotAvailableStatusException) {
            throw ForbiddenException(e.message!!)
        }
    }
}